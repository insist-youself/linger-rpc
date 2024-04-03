package com.linger.linrpc.loadbalancer;

import com.linger.linrpc.spi.SpiLoader;

/**
 * 负载均衡工厂 （工厂模式，用于获取负载均衡对象）
 *
 * @author linger
 * @date 2024/4/1 16:26
 */
public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取实例
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }
}
