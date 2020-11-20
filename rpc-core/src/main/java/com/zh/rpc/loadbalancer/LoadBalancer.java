package com.zh.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author Space_Pig
 * @date 2020/11/14 18:19
 */
public interface LoadBalancer {
    Instance select(List<Instance> instances);
}
