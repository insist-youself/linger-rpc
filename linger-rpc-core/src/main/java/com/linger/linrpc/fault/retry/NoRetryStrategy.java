package com.linger.linrpc.fault.retry;

import com.linger.linrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 不重试 —— 重试策略
 *
 * @author linger
 * @date 2024/4/1 18:43
 */
public class NoRetryStrategy implements RetryStrategy{

    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */

    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
