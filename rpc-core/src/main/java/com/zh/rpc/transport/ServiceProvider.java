package com.zh.rpc.transport;

/**
 * 服务注册表
 * @author Space_Pig
 * @date 2020/11/03 9:57
 */
public interface ServiceProvider {
    <T> void addServiceProvider(T service);
    Object getServiceProvider(String serviceName);
}
