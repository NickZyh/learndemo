package com.netty.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Author Zyh
 * @Date 2019/8/25 22:08
 * @Description
 * @Note
 */
@SuppressWarnings("all")
public class ChatClient {

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 客户端启动辅助类
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());

            // 异步连接到远程服务器, 服务器 -> 客户端
            Channel channel = bootstrap.connect("localhost", 8081).sync().channel();

            // 构建IO流 客户端 -> 服务器
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                // readLine是一个阻塞方法,不断监听控制台输入的消息
                channel.writeAndFlush(reader.readLine() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
