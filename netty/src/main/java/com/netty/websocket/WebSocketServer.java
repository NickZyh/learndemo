package com.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Zyh on 2019/8/31.
 */
@SuppressWarnings("all")
public class WebSocketServer {
    /**
     * websocket是一种规范,是http请求的升级协议,是一种长连接;长连接的意
     * 思就是正常情况下这个连接不会断开.并且连接两个的地位是平等的.
     * websocket只有在第一次连接的时候会传送额外请求信息(标准http请求),之
     * 后每次交互都是基于通道来进行数据传输,即向通道中写入数据,浏览器端不会
     * 发出请求.
     *
     * WebSocket的frame的概念：当连接建立完毕后,客户端和服务器端不会以请求
     * 方式发送数据,而是以管道进行交互,每次在管道中数据交互就是frame,frame就
     * 可以理解为每次交互的一个数据包,是一个载体.
     */

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketChannelInitializer());

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
