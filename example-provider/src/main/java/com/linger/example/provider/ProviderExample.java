package com.linger.example.provider;

import com.linger.example.common.model.User;
import com.linger.example.common.service.UserService;
import com.linger.linrpc.RpcApplication;
import com.linger.linrpc.registry.LocalRegistry;
import com.linger.linrpc.server.HttpServer;
import com.linger.linrpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 *
 * @author linger
 * @date 2024/3/23 22:28
 */
public class ProviderExample {
    public static void main(String[] args) {
        //RPC框架服务
        RpcApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
