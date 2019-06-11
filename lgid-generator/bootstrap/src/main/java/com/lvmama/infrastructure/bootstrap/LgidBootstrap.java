package com.lvmama.infrastructure.bootstrap;

import com.lvmama.infrastructure.bootstrap.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: dengcheng
 * @Date: 2019/5/20 15:44
 * @Description:
 */
public class LgidBootstrap {

    public static Map<Channel, MySQLSession> sessions = new ConcurrentHashMap<Channel, MySQLSession>();


    public static void main(String[] args) throws  Exception {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
//            final MysqlProtocalHandler serverHandler = new MysqlProtocalHandler();
//            ChannelInitializer<SocketChannel> socketChannelChannelInitializer = ()-> {
////                ch.pipeline().addLast(serverHandler);
//            };
//
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(3306))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new MysqlHandshakeOutboundHandler());
                            ch.pipeline().addLast(new MysqlPacketMessageEncoder());
                            ch.pipeline().addLast(new MysqlPacketByteToMessageDecoder());
                            ch.pipeline().addLast(new MysqlHandShakeInboundHandler());
                            ch.pipeline().addLast(new MysqlTextProtocalInboundHandler());



//                            ch.pipeline().addLast(new Channelout)
                        }
                    });
            ChannelFuture f = bootstrap.bind().sync();
            f.channel().closeFuture().sync();
            System.out.println("processor num:"+ NettyRuntime.availableProcessors());
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
