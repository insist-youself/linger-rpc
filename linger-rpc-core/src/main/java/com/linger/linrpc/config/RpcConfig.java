package com.linger.linrpc.config;

import com.linger.linrpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * 框架配置类
 *
 * @author linger
 * @date 2024/3/23 21:29
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "linger-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 端口号
     */
    private Integer serverPort = 8081;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;
}
