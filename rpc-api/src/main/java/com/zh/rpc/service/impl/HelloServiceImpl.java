package com.zh.rpc.service.impl;

import com.zh.rpc.entiy.HelloObject;
import com.zh.rpc.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Space_Pig
 * @date 2020/10/31 18:29
 * 测试
 */
public class HelloServiceImpl implements HelloService {
    public static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject helloObj) {
        LOGGER.info("接收到：{}，收到返回值：{}",helloObj.getMessage(),helloObj.getId());
        return "收到客户端的返回值，id=" + helloObj.getId();
    }
}
