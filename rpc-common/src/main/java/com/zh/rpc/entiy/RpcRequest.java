package com.zh.rpc.entiy;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Space_Pig
 * @date 2020/11/01 10:07
 */

@Data
@Builder
public class RpcRequest implements Serializable {

    /**
     * 接口名字
     */
    private String interfaceName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 方法参数
     */
    private Object[] parameters;
    /**
     * 方法参数类型
     */
    private Class<?>[] paramTypes;

}
