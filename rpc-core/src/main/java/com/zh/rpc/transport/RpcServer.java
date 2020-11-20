package com.zh.rpc.transport;

/**
 * @author Space_Pig
 * @date 2020/11/09 22:56
 */
public interface RpcServer {
    void start(int port);
    <T>void publishService(Object service,Class<T> serviceClass);
}
