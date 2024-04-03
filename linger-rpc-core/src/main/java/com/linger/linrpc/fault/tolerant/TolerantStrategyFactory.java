package com.linger.linrpc.fault.tolerant;

import com.linger.linrpc.spi.SpiLoader;

/**
 * 容错策略工厂 （工厂模式， 用于容错策略对象的获取）
 *
 * @author linger
 * @date 2024/4/1 21:51
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    public static final TolerantStrategy DEFAULT_TOLERANT_STRATEGY =
            new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key){
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
