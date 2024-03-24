package com.linger.linrpc.server;

import io.vertx.core.Vertx;

/**
 * 基于 Vert.x 实现的 web 服务器
 *
 * @author linger
 * @date 2024/3/21 18:55
 */
public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 Http 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 监听端口并处理请求
        server.requestHandler(new HttpServerHandler());

        // 启动 HTTP 服务器并监听指定端口
        server.listen(port, result ->{
                if (result.succeeded()) {
                    System.out.println("Server is now listening on port " + port);
                } else {
                    System.out.println("Failed to start server: " + result.cause());
                }
        });
    }
}
