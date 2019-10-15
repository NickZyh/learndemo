package com.netty.protobuf.example3.client;

import com.netty.protobuf.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * Created by Zyh on 2019/9/1.
 */
@SuppressWarnings("all")
public class ProtoBufClientHandler extends SimpleChannelInboundHandler<MyDataInfo.Person> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 这里是为了保证随机性,表示客户端随意传值
        int randomInt = new Random().nextInt(3);

        MyDataInfo.MyMessage myMessage;
        // 将.proto文件中的type枚举与此处的随机数进行关联
        if (0 == randomInt) {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                    .setPerson(MyDataInfo.Person.newBuilder()
                            .setName("人").setAge(20).setAddress("深圳")
                            .build())
                    .build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder()
                            .setName("学生").setAge(20).setAddress("教室")
                            .build())
                    .build();
        }

        ctx.channel().writeAndFlush(myMessage);
    }
}
