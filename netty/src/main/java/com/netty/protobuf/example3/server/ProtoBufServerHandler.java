package com.netty.protobuf.example3.server;

import com.netty.protobuf.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Zyh on 2019/9/1.
 */
@SuppressWarnings("all")
public class ProtoBufServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == MyDataInfo.MyMessage.DataType.PersonType) {
            MyDataInfo.Person person = msg.getPerson();
            System.out.println(person.getName() + ":" + person.getAge() + ":" + person.getAddress());
        } else if (dataType == MyDataInfo.MyMessage.DataType.StudentType) {
            MyDataInfo.Person person = msg.getPerson();
            System.out.println(person.getName() + ":" + person.getAge() + ":" + person.getAddress());
        } else {
            System.out.println("没有对应的值");
        }
    }
}
