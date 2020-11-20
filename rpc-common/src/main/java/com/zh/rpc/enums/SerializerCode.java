package com.zh.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字节流中标记序列化和反序列化器
 * @author Space_Pig
 * @date 2020/11/08 23:55
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {
    KRYO(0),
    JSON(1),
    HESSIAN(2),
    PROTOBUF(3);

    private final int code;

}
