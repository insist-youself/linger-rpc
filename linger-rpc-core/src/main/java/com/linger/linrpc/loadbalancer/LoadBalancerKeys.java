package com.linger.linrpc.loadbalancer;

/**
 * 负载均衡器键名常量
 *
 * @author linger
 * @date 2024/4/1 16:19
 */
public interface LoadBalancerKeys {

    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";
}
