package com.linger.linrpc.fault.tolerant;

/**
 * 容错策略键名常量
 *
 * @author linger
 * @date 2024/4/1 21:48
 */
public interface TolerantStrategyKeys {

    /**
     * 故障恢复
     */
    String FALL_BACK = "failBack";

    /**
     * 快速失败
     */
    String FAIL_FAST = "failFast";

    /**
     * 故障转移
     */
    String FAIL_OVER = "failOver";

    /**
     * 静默处理
     */
    String FAIL_SAFE = "failSafe";

}
