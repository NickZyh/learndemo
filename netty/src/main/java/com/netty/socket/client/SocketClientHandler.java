package com.netty.socket.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * @Author Zyh
 * @Date 2019/8/30 21:34
 * @Description
 * @Note
 */
public class SocketClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress());
        System.out.println("client out: " + msg);
        channel.writeAndFlush("from client: " + LocalDateTime.now());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 当连接成功后,这个方法会被调用,所以我们在这个方法中向服务端发送一条数据,模拟运行一下
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 监听控制台输入
        ctx.writeAndFlush(System.in);
    }
}
