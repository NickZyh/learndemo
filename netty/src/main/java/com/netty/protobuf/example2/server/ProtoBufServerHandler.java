package com.netty.protobuf.example2.server;

import com.netty.protobuf.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Zyh on 2019/9/1.
 */
public class ProtoBufServerHandler extends SimpleChannelInboundHandler<MyDataInfo.Person> {

    /**
     * @param ctx
     * @param msg  MyDataInfo.Person实际的载体
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person msg) throws Exception {
        System.out.println(msg.getName() + ":" + msg.getAge() + ":" + msg.getAddress());
    }
}
