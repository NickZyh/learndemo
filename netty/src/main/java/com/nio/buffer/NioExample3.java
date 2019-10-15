package com.nio.buffer;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Zyh on 2019/9/6.
 */
public class NioExample3 {

    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("netty/NioExample3.txt");
        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        byte[] bytes = "hello world welcome".getBytes();
        for (byte b : bytes) {
            // 所有的数据都是先放到buffer中,然后才能操作channel来进行读写
            byteBuffer.put(b);
        }

        // 状态反转  写buffer -> 读buffer
        byteBuffer.flip();

        // 读取buffer中的数据写入到channel中
        channel.write(byteBuffer);

        channel.close();
    }
}
