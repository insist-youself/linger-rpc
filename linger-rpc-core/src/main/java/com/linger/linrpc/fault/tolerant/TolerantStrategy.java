package com.linger.linrpc.fault.tolerant;

import com.linger.linrpc.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略
 *
 * @author linger
 * @date 2024/4/1 21:37
 */
public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context   上下文，用于传递暑假
     * @param e         异常
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
