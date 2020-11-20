package com.zh.rpc.transport.server;

import com.zh.rpc.enums.RpcError;
import com.zh.rpc.transport.ServiceProvider;
import com.zh.rpc.until.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Space_Pig
 * @date 2020/11/03 10:11
 */
public class ServiceProviderImpl implements ServiceProvider {
    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderImpl.class);

    //用于查询保存的方法
    private final Map<String,Object> serviceMap = new ConcurrentHashMap<>();
    //存储方法
    private final Set<String> registeredSet = ConcurrentHashMap.newKeySet();

    @Override
    public <T> void addServiceProvider(T service) {
        //获取完整的方法的包名
        String canonicalName = service.getClass().getCanonicalName();
        if (serviceMap.containsKey(canonicalName))return;
        registeredSet.add(canonicalName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0){
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> anInterface : interfaces) {
            serviceMap.put(anInterface.getName(),service);
        }
        logger.info("向接口：{} 注册服务：{}",interfaces,service);
    }

    @Override
    public Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null){
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
