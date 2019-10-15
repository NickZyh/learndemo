package com.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @Author Zyh
 * @Date 2019/8/25 21:36
 * @Description
 * @Note
 */
@SuppressWarnings("all")
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 第二个参数和泛型有关,默认就是Object,如下注释的方法
     * @param channelHandlerContext  channel的上下文
     * @param httpObject
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest) { // HttpRequest是接口,当是http请求时,默认实现类是DefaultHttpRequest
            HttpRequest httpRequest = (HttpRequest)httpObject;
            // 请求方式
            System.out.println("请求方式:" + httpRequest.method().name() + " uri:" + httpRequest.uri());
            // 获取请求uri
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                return;
            }

            // 获取到当前的channel
            Channel channel = channelHandlerContext.channel();
            // 使用ByteBuf构建响应内容
            ByteBuf content = Unpooled.copiedBuffer("Hello " + channel.remoteAddress(), CharsetUtil.UTF_8);
            // 构造一个http响应信息
            FullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(),
                    HttpResponseStatus.OK, content);
            // 设置头信息
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 单独只使用write会返回不了,因为write只会写到缓冲区,所以需要Flush一下刷缓冲下
            channelHandlerContext.writeAndFlush(response);

            // 关闭当前连接,http1.1是长连接,1.0是短连接;这是对协议的具体实现
            if (HttpVersion.HTTP_1_0.equals(httpRequest.protocolVersion())) {
                channelHandlerContext.close();
            }
        }
    }

    /**
     * 通道处于活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active");
        super.channelActive(ctx);
    }

    /**
     * 通道被注册
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered");
        super.channelRegistered(ctx);
    }

    /**
     * 当连接建立完成时调用 - 有一个新的处理器被添加
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inActive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel removed");
        super.handlerRemoved(ctx);
    }

    /**
     * 使用浏览器访问的执行流程：
     * 1 handler added：handler被添加进通道,说明有一个新的通道被添加
     * 2 channel registered：通道被注册,channel被注册
     * 3 channel active:通道处于活动状态,channel处于活动状态
     * 4 开始执行请求
     * 要注意这里并不会执行unregistered和inactive方法.这两个方法当socket断开连接时才会执行.
     * http1.1时,服务器会保持一定的时间,注意这是规范,具体是要由服务器来具体实现的.tcp协议是
     * 长连接协议,并不会自动断开,除非有一方断开连接,所以当把浏览器关闭的时候,浏览器和服务器断开,此
     * 时就会执行.
     */
}
