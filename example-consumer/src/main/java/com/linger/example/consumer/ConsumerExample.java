package com.linger.example.consumer;

import com.linger.linrpc.config.RpcConfig;
import com.linger.linrpc.utils.ConfigUtils;

/**
 * 简易服务消费者示例
 *
 * @author linger
 * @date 2024/3/23 22:20
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
