package com.linger.linrpc.registry;

import com.linger.linrpc.config.RegistryConfig;
import com.linger.linrpc.model.ServiceMetaInfo;
import com.linger.linrpc.serializer.SerializerFactory;

import java.util.List;

/**
 * 注册中心
 *
 * @author linger
 * @date 2024/3/28 16:45
 */
public interface Registry {

    /**
     * 初始化
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     *
     * @param serviceMetaInfo
     * @throws Exception
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     *
     * @param serviceMetaInfo
     */

    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     *
     * @param serviceKey 服务键名
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测（服务端）
     */
    void heartBeat();

    /**
     *  监听 （消费端）
     */
    void watch(String serviceNodeKey);
}
