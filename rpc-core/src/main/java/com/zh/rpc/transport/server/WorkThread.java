package com.zh.rpc.transport.server;

import com.zh.rpc.entiy.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author Space_Pig
 * @date 2020/11/01 12:05
 */
public class WorkThread implements Runnable{

    Socket socket;
    Object service;

    private static final Logger logger = LoggerFactory.getLogger(WorkThread.class);

    public WorkThread(Socket socket, Object service){
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try(ObjectInputStream oisIn = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oosOut = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest)oisIn.readObject();
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParamTypes());
            Object returnObject = method.invoke(service, rpcRequest.getParameters());
            oosOut.writeObject(returnObject);
            oosOut.flush();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
            logger.error("接受rpcRequest对象失败：{}",e);
        }
    }
}
