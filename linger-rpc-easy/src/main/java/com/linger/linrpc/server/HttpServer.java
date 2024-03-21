package com.linger.linrpc.server;

/**
 * Http 服务接口
 *
 * @author linger
 * @date 2024/3/21 18:54
 */
public interface HttpServer {

    /**
     * 启动服务器
     *
     * @param port
     */
    void doStart(int port);
}
