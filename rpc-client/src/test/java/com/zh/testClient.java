package com.zh;

import com.zh.rpc.entiy.HelloObject;
import com.zh.rpc.proxy.RpcClientProxy;
import com.zh.rpc.service.HelloService;

/**
 * @author Space_Pig
 * @date 2020/11/01 12:49
 */
public class testClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 8000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject("This is a message",2020);
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
