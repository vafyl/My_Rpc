package com.zh.rpc.transport;

import com.zh.rpc.entiy.RpcRequest;
import com.zh.rpc.entiy.RpcResponse;
import com.zh.rpc.serializer.CommonSerializer;
import com.zh.rpc.transport.Client.NettyClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 使用jdk动态代理方式实现生产实例
 * @author Space_Pig
 * @date 2020/11/01 10:53
 */

public class RpcClientProxy implements InvocationHandler {

    private String host;
    private int port;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder().
                interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        com.zh.rpc.transport.Client.NettyClient rpcClient = new NettyClient(CommonSerializer.getByCode(0));
        return ((RpcResponse) rpcClient.sendRequest(rpcRequest)).getData();
    }
}