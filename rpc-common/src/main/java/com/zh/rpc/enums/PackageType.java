package com.zh.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Space_Pig
 * @date 2020/11/09 0:02
 */
@AllArgsConstructor
@Getter
public enum PackageType {
    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}
