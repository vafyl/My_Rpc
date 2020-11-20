package com.zh.rpc.until;

/**
 * 序列化异常
 * @author Space_Pig
 * @date 2020/11/09 22:43
 */
public class SerializeException extends RuntimeException{
    public SerializeException(String message) {
        super(message);
    }
}
