package com.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author Zyh
 * @Date 2019/8/25 21:03
 * @Description
 * @Note
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 传入的参数由泛型决定,如果不加泛型默认为Channel对象,如下注释的方法
     * 这个方法就是用于构建channel中的pipeline,即添加handler处理器链
     * netty处理请求的流程就是调用一整个处理链的流程
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 构建pipeline,一个pipeline就能够理解为是由handler构成的处理管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        /**
         * handler分为三类,每一类都各自成顺序
         * Inbound:读取数据到服务端调用
         * Outbound：服务端写出数据调用
         * Duplex：出栈和入栈都调用
         */
        // 这个处理器用于处理http请求,包括编码和解码两个处理器,也就是对http协议的封装
        // 编码：将数据转成二进制数据
        // 解码：将二进制数据解析成数据
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        // 定义一个我们自己的handler
        pipeline.addLast("httpServerHandler", new HttpServerHandler());
    }

//    protected void initChannel(HttpObject httpObject) throws Exception {
//
//    }
//
//
//    protected void initChannel(Channel channel) throws Exception {
//
//    }
}