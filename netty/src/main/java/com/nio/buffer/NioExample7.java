package com.nio.buffer;

import java.nio.ByteBuffer;

/**
 * Created by Zyh on 2019/9/10.
 */
public class NioExample7 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        /**
         * 按类型存放的方式.任何数据在底层都是byte类型;
         */
        buffer.putInt(15);
        buffer.putLong(50000000L);
        buffer.putDouble(14.23213321);
        buffer.putChar('你');
        buffer.putShort((short)2);

        buffer.flip();

        /**
         * 使用按类型的方式放入,取出来也需要用对应类型的方法;
         * 并且取出的时候也需要按放入的顺序取出,这是因为buffer中的字节是按照顺序放入的
         */
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
    }
}
