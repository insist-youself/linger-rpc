package com.linger.linrpc.bootstrap;

import com.linger.linrpc.RpcApplication;

/**
 * 服务消费者启动类 （初始化）
 *
 * @author linger
 * @date 2024/4/2 19:48
 */
public class ConsumerBootstrap {

    /**
     * 初始化
     */
    public static void init() {
        // 框架初始化和注册中心
        RpcApplication.init();
    }
}
