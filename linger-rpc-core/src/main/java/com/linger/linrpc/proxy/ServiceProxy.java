package com.linger.linrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.linger.linrpc.RpcApplication;
import com.linger.linrpc.config.RpcConfig;
import com.linger.linrpc.constant.RpcConstant;
import com.linger.linrpc.fault.retry.RetryStrategy;
import com.linger.linrpc.fault.retry.RetryStrategyFactory;
import com.linger.linrpc.fault.tolerant.TolerantStrategy;
import com.linger.linrpc.fault.tolerant.TolerantStrategyFactory;
import com.linger.linrpc.loadbalancer.LoadBalancer;
import com.linger.linrpc.loadbalancer.LoadBalancerFactory;
import com.linger.linrpc.model.RpcRequest;
import com.linger.linrpc.model.RpcResponse;
import com.linger.linrpc.model.ServiceMetaInfo;
import com.linger.linrpc.protocol.*;
import com.linger.linrpc.registry.Registry;
import com.linger.linrpc.registry.RegistryFactory;
import com.linger.linrpc.serializer.JdkSerializer;
import com.linger.linrpc.serializer.Serializer;
import com.linger.linrpc.serializer.SerializerFactory;
import com.linger.linrpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 服务代理 (JDK 动态代理 )
 *
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


        //序列化
        //byte[] bodyBytes = serializer.serialize(rpcRequest);

        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("暂无服务地址");
        }
        // 固定获取第一个服务节点
        //ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);
        // 负载均衡
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalance());
        // 将调用方法名（请求路径）作为负载均衡的参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServiceMetaInfo selectedServiceMetaInfo =
                loadBalancer.select(requestParams, serviceMetaInfoList);

        // 重试机制
        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy =
                    RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            // 发送 rpc (TCP) 请求
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
        } catch (Exception e) {
            // 容错策略
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory
                    .getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null, e);
            //throw new RuntimeException("调用失败");
        }
        return rpcResponse.getData();

    }

    /**
     * 发送 HTTP 请求
     *
     * @param selectedServiceMetaInfo
     * @param bodyBytes
     * @return
     * @throws IOException
     */
    private static RpcResponse doHttpRequest(ServiceMetaInfo selectedServiceMetaInfo, byte[] bodyBytes) throws IOException {
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        // 发送 HTTP 请求
        try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                .body(bodyBytes)
                .execute()) {
            byte[] result = httpResponse.bodyBytes();
            // 反序列化
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse;
        }
    }
}
