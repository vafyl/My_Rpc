package com.zh.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 轮询方式顺序使用服务
 * @author Space_Pig
 * @date 2020/11/14 18:24
 */
public class RandomRobinLoadBalancer implements LoadBalancer{

    private int index = 0;
    @Override
    public Instance select(List<Instance> instances) {
        if (index <= instances.size()){
            index %= instances.size();
        }
        return instances.get(index++);
    }
}
