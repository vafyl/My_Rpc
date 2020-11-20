package com.zh.rpc.until;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.zh.rpc.enums.RpcError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Nacos相关管理工具
 * @author Space_Pig
 * @date 2020/11/14 17:08
 */
public class NacosUtils {
   private static final Logger logger = LoggerFactory.getLogger(NacosUtils.class);
   private static final NamingService namingService;
   private static final Set<String> serviceNames = new HashSet<>();
   private static InetSocketAddress address;

   private static final String SERVER_ADDR = "127.0.0.1:8848";

   static {
      namingService = getNacosNamingService();
   }

   public static NamingService getNacosNamingService(){
      try {
         return NamingFactory.createNamingService(SERVER_ADDR);
      } catch (NacosException e) {
         logger.error("连接到Nacos异常：{}",e);
         throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
      }
   }
   public static void registerService(String serviceName,InetSocketAddress socketAddress){
      try {
         namingService.registerInstance(serviceName,socketAddress.getHostName(),socketAddress.getPort());
         NacosUtils.address = address;
         serviceNames.add(serviceName);
      } catch (NacosException e) {
         logger.error("注册服务失败：{}",e);
         throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
      }
   }

   public static List<Instance> getAllInstances(String serviceName){
      try {
         return namingService.getAllInstances(serviceName);
      } catch (NacosException e) {
         logger.error("获取服务信息失败：{}",e);
         throw new RpcException(RpcError.SERVICE_NOT_FOUND);      }
   }

   public static void clearRegistry(){
      if (!serviceNames.isEmpty() && address != null){
         String host = address.getHostName();
         int port = address.getPort();
         Iterator<String> iterator = serviceNames.iterator();
         while (iterator.hasNext()){
            String serviceName = iterator.next();
            try {
               namingService.deregisterInstance(serviceName,host,port);
            } catch (NacosException e) {
               logger.error("注销服务{}失败",serviceName,e);
            }
         }
      }
   }

}
