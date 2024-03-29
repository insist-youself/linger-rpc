package com.linger.linrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.linger.linrpc.RpcApplication;
import com.linger.linrpc.config.RpcConfig;
import com.linger.linrpc.constant.RpcConstant;
import com.linger.linrpc.model.RpcRequest;
import com.linger.linrpc.model.RpcResponse;
import com.linger.linrpc.model.ServiceMetaInfo;
import com.linger.linrpc.registry.Registry;
import com.linger.linrpc.registry.RegistryFactory;
import com.linger.linrpc.serializer.JdkSerializer;
import com.linger.linrpc.serializer.Serializer;
import com.linger.linrpc.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 服务代理 (JDK 动态代理 )
 * @author linger
 * @date 2024/3/21 22:02
 */
public class ServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer =
                SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        //构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterType(method.getParameterTypes())
                .args(args)
                .build();

        try {
            //序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)){
                throw new RuntimeException("暂无服务地址");
            }
            // 暂时先取第一个
            ServiceMetaInfo seletedServiceMetaInfo = serviceMetaInfoList.get(0);

            try (HttpResponse httpResponse = HttpRequest.post(seletedServiceMetaInfo.getServiceAddress())
                         .body(bodyBytes)
                         .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
