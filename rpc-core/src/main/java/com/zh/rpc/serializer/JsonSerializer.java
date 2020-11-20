package com.zh.rpc.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zh.rpc.entiy.RpcRequest;
import com.zh.rpc.enums.SerializerCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Space_Pig
 * @date 2020/11/08 23:34
 */
public class JsonSerializer implements CommonSerializer{
    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(Object object) {

        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            logger.error("序列化有错误：{}",e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Object desSerialize(byte[] bytes, Class<?> clazz) {
        try {
            Object o = objectMapper.readValue(bytes, clazz);
            if (o instanceof RpcRequest){
                o = handleRequest(o);
            }
            return o;
        } catch (IOException e) {
            logger.error("序列化有错误：{}",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("JOSN").getCode();
    }

    /**
     * 重新处理反序列化的数据
     * @param obj
     * @return
     * @throws IOException
     */
    private Object handleRequest(Object obj) throws IOException {
        RpcRequest rpcRequest =(RpcRequest)obj;
        for (int i=0;i < rpcRequest.getParamTypes().length;i++){
            Class<?> paramType = rpcRequest.getParamTypes()[i];
            if (paramType.isAssignableFrom(rpcRequest.getParameters().getClass())){
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParameters()[i]);
                rpcRequest.getParameters()[i]  = objectMapper.readValue(bytes, paramType);
            }
        }
        return rpcRequest;
    }
}
