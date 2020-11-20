package com.zh.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 服务注册接口
 * @author Space_Pig
 * @date 2020/11/13 22:46
 */
public interface ServiceRegistry {
    void register(String serviceName, InetSocketAddress inetSocketAddress);
    InetSocketAddress lookupService(String serviceName);
}
