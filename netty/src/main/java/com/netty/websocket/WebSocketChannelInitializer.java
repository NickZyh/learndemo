package com.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * Created by Zyh on 2019/8/31.
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // websocket是升级版的http请求,所以需要http编解码器
        pipeline.addLast(new HttpServerCodec());
        // 基于块的大量数据处理器(待整理)
        pipeline.addLast(new ChunkedWriteHandler());
        // HTTP请求聚合器；netty的http请求可能会切分成多个块来处理,并且每个块都会执行
        // 一次流程;这个处理器的作用就是把多个块组成一个http请求,构造方法中传入的参数就是
        // 最大长度
        pipeline.addLast(new HttpObjectAggregator(8192));
        // websocket请求的处理器,websocket的数据以frame的结构来进行传递的.
        // 构造参数传递的是contextPath,websocket请求的传递参数为 ws://server:port/context-path
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new WebSocketServerHandler());
    }
}
