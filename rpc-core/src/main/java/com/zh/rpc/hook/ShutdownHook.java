package com.zh.rpc.hook;

import com.zh.rpc.factory.ThreadPoolFactory;
import com.zh.rpc.until.NacosUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 单例模式创建钩子，自动销毁服务 --饿汉式
 * @author Space_Pig
 * @date 2020/11/14 17:40
 */
public class ShutdownHook {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

    private static ShutdownHook shutdownHook = new ShutdownHook();

    private ShutdownHook() {}

    public static synchronized ShutdownHook getShutdownHook(){
        return shutdownHook;
    }

    public void addClearAllHook(){
        logger.info("关闭后将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtils.clearRegistry();
            ThreadPoolFactory.shutDownAll();
        }));
    }

//双重校验
/*    private volatile static ShutdownHook shutdownHook;
    private ShutdownHook(){};
    public static ShutdownHook getShutdownHook(){
        if (shutdownHook == null){
            synchronized (shutdownHook.getClass()){
                if (shutdownHook == null){
                    shutdownHook = new ShutdownHook();
                }
            }
        }
        return shutdownHook;
    }*/
}
