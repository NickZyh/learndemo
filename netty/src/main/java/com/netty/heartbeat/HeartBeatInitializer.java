package com.netty.heartbeat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author Zyh
 * @Date 2019/8/30 22:40
 * @Description
 * @Note
 */
public class HeartBeatInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 空闲状态检测的handler,读事件和写事件;当这个handler检测到事件之后,就会
        // 生成一个IdleStateEvent交给下一个handler去执行
        pipeline.addLast(new IdleStateHandler(5, 7,
                10, TimeUnit.SECONDS));
        pipeline.addLast(new HeartBeatHandler());
    }
}
