package com.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author Zyh
 * @Date 2019/8/25 20:48
 * @Description
 * @Note
 */
@SuppressWarnings("all")
public class HttpServer {

    /**
     * 1 定义循环组,包括boss和worker
     * 2 使用辅助类构建channel和填充handler进channel
     * 3 绑定端口,启动handler
     */

    public static void main(String[] args) {
        /**
         * EventLoopGroup:默认创建的线程数为当前cpu的核数 * 2;如果设置为thread数量则用设置的数
         * NioEventLoopGroup底层是使用NIO模型连接
         */
        // mainReactor,一般来说mainReactor线程只设置一个线程,使用一个端口来监听
        EventLoopGroup bossGroup = bossGroup = new NioEventLoopGroup(1);
        // subReactor
        EventLoopGroup workerGroup = workerGroup = new NioEventLoopGroup();;
        try {
            // 启动辅助类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 将reactor注册给启动类
            serverBootstrap.group(bossGroup, workerGroup)
                    // 服务端用什么channel,表示用什么方式创建Socket
                    .channel(NioServerSocketChannel.class)
                    // .handler()  这个handler是向服务器的pipline中添加handler的,在服务器启动过程中
                    // 就会添加完毕,handler()方法争对的是加入bossGroup,即group的第一个参数.
                    // 这个方法是用来定义我们自定义处理的pipline,在这个里面添加handler
                    // 这可以理解为构建我们自己的处理pipline,
                    .childHandler(new HttpServerInitializer());
            // 这里用于启动bossGroup端口,开始监听
            // 如果这个地方不改成同步的话,那么代码就会直接跑完,服务器就关闭了.
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            System.out.println("服务器启动完成");
            // netty中基本所有的操作都是基于异步的,sync()的意思是从异步变成同步.closeFuture具有阻塞功
            // 能,表示等待客户端关闭,如果这里不同步的话那么服务器就直接跑完了.则就直接关闭了,所以需要同步,阻塞起来.
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅的将boss和worker对应的线程池关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
