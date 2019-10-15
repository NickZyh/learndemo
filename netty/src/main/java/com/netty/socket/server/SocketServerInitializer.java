package com.netty.socket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @Author Zyh
 * @Date 2019/8/29 22:14
 * @Description
 * @Note
 */
@SuppressWarnings("all")
public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 当客户端和服务器端连接建立时,这个方法就会被调用
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 解码器
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                0,4 ,0, 4));
        // 编码器
        pipeline.addLast(new LengthFieldPrepender(4));
        // 字符串的编码和解码器
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        // 自定义实现的handler
        pipeline.addLast(new SocketServerHandler());
    }
}
