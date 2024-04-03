package com.linger.linrpc.fault.tolerant;

import com.google.protobuf.TimestampOrBuilder;
import com.linger.linrpc.model.RpcResponse;

import java.util.Map;

/**
 * 转移到其他服务节点 - 容错策略
 *
 * @author linger
 * @date 2024/4/1 21:46
 */
public class FailOverTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 可自行扩展，获取其他服务节点并调用
        return null;
    }
}
