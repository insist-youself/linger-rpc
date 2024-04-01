package com.linger.linrpc.loadbalancer;

import com.linger.linrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡器 （消费端使用）
 *
 * @author linger
 * @date 2024/4/1 15:44
 */
public interface LoadBalancer {

    /**
     * 选择服务调用
     *
     * @param requestParams         请求参数
     * @param serviceMetaInfoList   可用服务列表
     * @return
     */
    ServiceMetaInfo select(Map<String, Object> requestParams,
                           List<ServiceMetaInfo> serviceMetaInfoList);
}
