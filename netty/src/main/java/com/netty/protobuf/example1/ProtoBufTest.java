package com.netty.protobuf.example1;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by Zyh on 2019/9/1.
 */
public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张三").setAge(20).setAddress("深圳")
                .build();

        // 2就是to的简写；将student序列化成字节数组，即序列化
        byte[] student2ByteArray = student.toByteArray();

        // 这一段操作模拟byte[]数组在两台计算机之间通过网络进行传递.....

        // 反序列化
        DataInfo.Student student1 = DataInfo.Student.parseFrom(student2ByteArray);

        System.out.println(student1.getName());
        System.out.println(student1.getAge());
        System.out.println(student1.getAddress());

        /**
         * 这个程序实际上是特别有实际意义的,因为使用toByteArray()将对象转成了字节数组,即完成序列化.
         * 此时可以将字节数组通过网络发送出去;然后再另一台机器上时可以用parseFrom()将字节数组重新转
         * 成对象.从而达到两台机器之间的数据传递.并且,这和语言无关,由于protobuf的封装,从而让不同语言
         * 编写的服务器之间能进行交互.
         */
    }
}
