package com.zh.rpc.until;


import com.zh.rpc.enums.RpcError;

/**
 * RPC异常
 * @author Space_Pig
 * @date 2020/11/03 10:55
 */
public class RpcException extends RuntimeException{
    public RpcException(RpcError rpcError){
        super(rpcError.getMessage());
    }
}
