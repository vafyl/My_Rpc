package com.zh.rpc.transport.server;

import com.zh.rpc.entiy.RpcRequest;
import com.zh.rpc.entiy.RpcResponse;
import com.zh.rpc.enums.RpcError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Space_Pig
 * @date 2020/11/07 13:40
 */
public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Object handler(RpcRequest rpcRequest,Object service){
        Object result = null;
        try {
            result= invokeTargetMethod(rpcRequest, service);
            logger.info("服务：{}，成功调用方法：{}",rpcRequest.getInterfaceName(),rpcRequest.getMethodName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
        return result;
    }
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(RpcError.SERVICE_NOT_FOUND);
        }
        return method.invoke(service,rpcRequest.getParameters());
    }
}
