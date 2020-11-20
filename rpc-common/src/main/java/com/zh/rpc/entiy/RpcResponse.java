package com.zh.rpc.entiy;

import com.zh.rpc.enums.RpcResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Space_Pig
 * @date 2020/11/01 10:16
 */
@Data
public class RpcResponse<T> implements Serializable {
    /**
     * 响应状态码
     */
    private Integer statusCode;
    /**
     * 响应状态补充信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应成功对象
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RpcResponse<T> success(T data){
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setStatusCode(RpcResponseCode.SUCCESS.getCode());
        rpcResponse.setMessage(RpcResponseCode.SUCCESS.getMessage());
        return rpcResponse;
    }

    /**
     * 响应失败对象
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RpcResponse<T> fail(T data){
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setStatusCode(RpcResponseCode.FAIL.getCode());
        rpcResponse.setMessage(RpcResponseCode.FAIL.getMessage());
        return rpcResponse;
    }

}
