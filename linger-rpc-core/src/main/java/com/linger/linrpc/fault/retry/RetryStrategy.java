package com.linger.linrpc.fault.retry;

import com.linger.linrpc.model.RpcResponse;
import dev.failsafe.Execution;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * 重试策略
 *
 * @author linger
 * @date 2024/4/1 18:40
 */
public interface RetryStrategy {

    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
