package com.nio.buffer;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Zyh on 2019/9/6.
 */
public class NioExample2 {

    public static void main(String[] args) throws Exception {
        // 路径为相对路径,这个相对路径是基于project来的.
        FileInputStream fileInputStream = new FileInputStream("netty/NioExample2.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        // 这个方法表示从channel中读取数据写入byteBuffer
        fileChannel.read(byteBuffer);

        // 状态反转 写buffer -> 读buffer
        byteBuffer.flip();

        // 当数据没有的时候是-1,当等于0时表示数据被获取完毕
        while (byteBuffer.remaining() > 0) {
            byte b = byteBuffer.get();
            System.out.println("char = " + (char)b);
        }

        // 关闭channel
        fileChannel.close();
    }
}
