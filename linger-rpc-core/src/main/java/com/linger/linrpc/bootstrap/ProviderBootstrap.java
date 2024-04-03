package com.linger.linrpc.bootstrap;

import com.linger.linrpc.RpcApplication;
import com.linger.linrpc.config.RegistryConfig;
import com.linger.linrpc.config.RpcConfig;
import com.linger.linrpc.model.ServiceMetaInfo;
import com.linger.linrpc.model.ServiceRegisterInfo;
import com.linger.linrpc.registry.LocalRegistry;
import com.linger.linrpc.registry.Registry;
import com.linger.linrpc.registry.RegistryFactory;
import com.linger.linrpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 *  服务提供者初始化
 *
 * @author linger
 * @date 2024/4/2 19:23
 */
public class ProviderBootstrap {

    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();

        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo: serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());

            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败", e);
            }
        }

        // 启动 web 服务
        //HttpServer httpServer = new VertxHttpServer();
        //httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

        // 启动 TCP 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }

}
