package com.zh.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.zh.rpc.loadbalancer.LoadBalancer;
import com.zh.rpc.loadbalancer.RandomLoadBalancer;
import com.zh.rpc.until.NacosUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Space_Pig
 * @date 2020/11/14 18:38
 */

public class NacosServiceDiscovery implements ServiceRegistry{
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceDiscovery.class);

    private final LoadBalancer loadBalancer;

    public NacosServiceDiscovery(LoadBalancer loadBalancer) {
        if(loadBalancer == null) this.loadBalancer = new RandomLoadBalancer();
        else this.loadBalancer = loadBalancer;
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {

    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        List<Instance> allInstances = NacosUtils.getAllInstances(serviceName);
        Instance instance = loadBalancer.select(allInstances);
        return new InetSocketAddress(instance.getIp(),instance.getPort());
    }
}