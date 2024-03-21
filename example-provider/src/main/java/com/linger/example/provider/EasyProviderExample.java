package com.linger.example.provider;

import com.linger.linrpc.server.VertxHttpServer;

/**
 * 简易服务提供者实例
 *
 * @author linger
 * @date 2024/3/21 18:35
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // 启动 web 服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
