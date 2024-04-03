package com.linger.linrpc.fault.tolerant;

import com.linger.linrpc.model.RpcResponse;

import java.util.Map;

/**
 * 降级到其他服务 —— 容错策略
 *
 * @author linger
 * @date 2024/4/1 21:44
 */
public class FailBackTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 可自行扩展，获取降级的服务并调用
        return null;
    }
}
