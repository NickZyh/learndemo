package com.netty.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.UUID;

/**
 * @Author Zyh
 * @Date 2019/8/30 21:19
 * @Description
 * @Note
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * netty中提供的容器,可以用于保存channel
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        // 群发消息(除了它本身)
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + "发送的消息" + msg + "\n");
            } else {
                ch.writeAndFlush( "【自己】" + msg + "\n");
            }
        });
    }

    /**
     * 连接建立时被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 服务器向容器中的每一个channel中写数据
        channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "加入\n");
        // 当群发完之后,将当前的channel添加到channelGroup
        channelGroup.add(channel);
    }

    /**
     * 连接断开时
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 当群发完之后,将当前的channel移除channelGroup,实际上当连接断开时,netty会自动调用,所以其实可以不写
        channelGroup.remove(channel);
        // 服务器向容器中的每一个channel中写数据
        channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "断开\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
