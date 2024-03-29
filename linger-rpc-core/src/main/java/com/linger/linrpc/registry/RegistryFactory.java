package com.linger.linrpc.registry;

import com.linger.linrpc.spi.SpiLoader;

/**
 * 注册中心工厂 （用于获取注册中心对象）
 *
 * @author linger
 * @date 2024/3/28 19:38
 */
public class RegistryFactory {
    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    public static final Registry DEFAULT_REGISTRY = new EtcdRegistry();
    //public static final Registry DEFAULT_REGISTRY = new ZooKeeperRegistry();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Registry getInstance(String key){
        return SpiLoader.getInstance(Registry.class, key);
    }

}
