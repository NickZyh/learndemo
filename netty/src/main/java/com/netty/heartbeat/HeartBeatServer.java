package com.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author Zyh
 * @Date 2019/8/29 22:09
 * @Description
 * @Note
 *  这个程序是用来支持心跳机制的.因为对于TCP连接来说,正常关闭会让TCP连接断开;但是如果TCP连接
 *  没有使用四次挥手来断开连接的话,比如像一台手机开启了飞行模式,此时服务器是不会知道客户端已经
 *  断开连接了的,所以说如果此时服务器再像客户端发送数据则会导致数据丢失.
 *  HeartBeat就是采用心跳机制来检测客户端是否还存在.Netty中可以使用IdleStateHandler处理器
 *  来判断客户端是否还存活,其判断依据是连接到服务器的客户端在一段时间内是否产生了 读事件或者写
 *  事件,如果产生了则说明还存活;如果没有产生则说明客户端已经断线,此时服务器就可以来断开这个连接.
 */
@SuppressWarnings("all")
public class HeartBeatServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 能够打印出netty的启动过程
                    .childHandler(new HeartBeatInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8081).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
