package com.zh.rpc.transport;

import com.zh.rpc.entiy.RpcRequest;

/**
 * @author Space_Pig
 * @date 2020/11/09 0:26
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
