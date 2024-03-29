package com.linger.example.provider;

import com.linger.example.common.service.UserService;
import com.linger.linrpc.registry.LocalRegistry;
import com.linger.linrpc.server.VertxHttpServer;

/**
 * 简易服务提供者实例
 *
 * @author linger
 * @date 2024/3/21 18:35
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8081);
    }
}
