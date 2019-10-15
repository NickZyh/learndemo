package com.netty.protobuf.example3.server;

import com.netty.protobuf.MyDataInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Created by Zyh on 2019/9/1.
 */
@SuppressWarnings("all")
public class ProtoBufServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /**
         * netty的protobuf支持
         */
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        // 直接获取MyMessage对象
        pipeline.addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());

        pipeline.addLast(new ProtoBufServerHandler());

        /**
         * 问题:如果此处的MyDataInfo.Person写死的话,那么该服务器只能处理这一个类型的message,
         * 而实际上一个.proto文件种会包含多个定义好的message,并且都以内部类的形式存在于.proto
         * 文件生成的类中,那么我们应该如何编写不仅限于处理Person内部类的服务器和客户端呢？
         */
    }
}
