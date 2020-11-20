package com.zh.rpc.entiy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Space_Pig
 * @date 2020/10/31 18:12
 */

@Data
@AllArgsConstructor
public class HelloObject implements Serializable {
    private String message;
    private Integer id;
}
