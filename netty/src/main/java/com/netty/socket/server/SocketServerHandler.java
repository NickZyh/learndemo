package com.netty.socket.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @Author Zyh
 * @Date 2019/8/30 21:19
 * @Description
 * @Note
 */
public class SocketServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     *
     * @param ctx  channel上下文信息
     * @param msg  接收到的客户端发过来的实际数据
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + ":" + msg);
        channel.writeAndFlush("from server:" + UUID.randomUUID().toString());
    }

    /**
     * 当出现异常时的操作,一般都是关闭连接
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
