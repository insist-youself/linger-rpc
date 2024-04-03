package com.linger.linrpc.fault.retry;

/**
 * 重试策略键名常量
 *
 * @author linger
 * @date 2024/4/1 20:01
 */
public interface RetryStrategyKeys {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定间隔时间
     */
    String FIXED_INTERVAL = "fixedInterval";
}
