package com.linger.example.provider;

import com.linger.example.common.model.User;
import com.linger.example.common.service.UserService;
import com.linger.linrpc.RpcApplication;
import com.linger.linrpc.config.RegistryConfig;
import com.linger.linrpc.config.RpcConfig;
import com.linger.linrpc.model.ServiceMetaInfo;
import com.linger.linrpc.registry.LocalRegistry;
import com.linger.linrpc.registry.Registry;
import com.linger.linrpc.registry.RegistryFactory;
import com.linger.linrpc.server.HttpServer;
import com.linger.linrpc.server.VertxHttpServer;
import com.linger.linrpc.server.tcp.VertxTcpServer;

/**
 * 服务提供者示例
 *
 * @author linger
 * @date 2024/3/23 22:28
 */
public class ProviderExample {
    public static void main(String[] args) {
        //RPC框架服务
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());

        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        //HttpServer httpServer = new VertxHttpServer();
        //httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

        // 启动 TCP 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(8081);
    }
}
