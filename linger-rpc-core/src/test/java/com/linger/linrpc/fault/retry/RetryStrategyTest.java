package com.linger.linrpc.fault.retry;

import com.linger.linrpc.model.RpcResponse;
import org.junit.Test;

/**
 * @author linger
 * @date 2024/4/1 19:55
 */
public class RetryStrategyTest {

    RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();

    @Test
    public void doRetry(){
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }

    }
}
