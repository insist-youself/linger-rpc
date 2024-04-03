package com.linger.linrpc.fault.tolerant;

import com.linger.linrpc.model.RpcResponse;

import java.util.Map;

/**
 * 快速失败 - 容错策略
 *
 * @author linger
 * @date 2024/4/1 21:40
 */
public class FailFastTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
