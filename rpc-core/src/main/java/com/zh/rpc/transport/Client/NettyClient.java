package com.zh.rpc.transport.Client;

import com.zh.rpc.codec.CommonDecoder;
import com.zh.rpc.codec.CommonEncoder;
import com.zh.rpc.entiy.RpcRequest;
import com.zh.rpc.entiy.RpcResponse;
import com.zh.rpc.enums.RpcError;
import com.zh.rpc.registry.NacosServiceRegistry;
import com.zh.rpc.registry.ServiceRegistry;
import com.zh.rpc.serializer.CommonSerializer;
import com.zh.rpc.serializer.KryoSerializer;
import com.zh.rpc.transport.RpcClient;
import com.zh.rpc.transport.ServiceProvider;
import com.zh.rpc.transport.server.ServiceProviderImpl;
import com.zh.rpc.until.RpcException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;


/**
 * @author Space_Pig
 * @date 2020/11/09 0:28
 */
public class NettyClient implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    private final CommonSerializer serializer;

    private final ServiceRegistry serviceRegistry = new NacosServiceRegistry();
    private final ServiceProvider serviceProvider = new ServiceProviderImpl();



    public NettyClient(CommonSerializer serializer) {
        this.serializer = serializer;
    }


    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new KryoSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });

    }

    /**
     * 获取发送的对象
     * @param rpcRequest
     * @return
     */
    public Object sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        AtomicReference<Object> result = new AtomicReference<>(null);

        try {
            InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);

            logger.info("客户端连接到服务器 {}:{}", host, port);
            if (channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        logger.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse.getData();
            }
        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生: ", e);
        }
        return null;
    }
}
