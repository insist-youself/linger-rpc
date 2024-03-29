package com.linger.linrpc.registry;

import com.linger.linrpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author linger
 * @date 2024/3/29 16:23
 */
public class RegistryServiceCache {

    /**
     *  服务缓存
     */
    List<ServiceMetaInfo> serviceCache;

    void writeCache(List<ServiceMetaInfo> newServiceCache) {
        this.serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     * @return
     */
    List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    void clearCache() {
        this.serviceCache = null;
    }
}
