package com.linger.linrpc.config;

import com.linger.linrpc.registry.RegistryKeys;
import lombok.Data;

/**
 * RPC 框架注册中心配置
 *
 * @author linger
 * @date 2024/3/28 16:39
 */
@Data
public class RegistryConfig {

    /**
     * 注册中心类别
     */
    private String registry = RegistryKeys.ETCD;
    //private String registry = RegistryKeys.ZOOKEEPER;


    /**
     * 注册中心地址
     */
    private String address = "http://localhost:2380";
    //private String address = "localhost:2181";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超过时间 （单位毫秒）
     */
    private Long timeout = 10000L;
}
