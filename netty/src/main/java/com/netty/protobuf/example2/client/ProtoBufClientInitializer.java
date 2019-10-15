package com.netty.protobuf.example2.client;

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
public class ProtoBufClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /**
         * netty的protobuf支持
         */
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        // 将字节数组转成对应的对象,传入的参数是需要转化的消息对应的内部类实例
        pipeline.addLast(new ProtobufDecoder(MyDataInfo.Person.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());

        pipeline.addLast(new ProtoBufClientHandler());
    }
}
