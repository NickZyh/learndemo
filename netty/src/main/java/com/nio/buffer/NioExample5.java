package com.nio.buffer;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Created by Zyh on 2019/9/8.
 */
@SuppressWarnings("all")
public class NioExample5 {

    public static void main(String[] args) {
        // 生成一个IntBuffer,底层为一个byte[]数组,allocate为声明一个堆内内存HeapIntBuffer
        // 参数为byte[]的容量capacity
        IntBuffer buffer = IntBuffer.allocate(10);

        for(int i = 0; i < 5; i++) {
            // SecureRandom生成的随机数更随机,表示生成0 - 20以内的随机数
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        // 10
        System.out.println("before flip limit: " + buffer.limit());
        // 翻转
        buffer.flip();
        // 5
        System.out.println("after flip limit: " + buffer.limit());

        // 当buffer中还存在数据时,就读取并打印出来
        while (buffer.hasRemaining()) {
            // 0，1，2，3，4  // 5  // 10
            System.out.println("position: " + buffer.position() + ";limit: " + buffer.limit()
                    + ";capacity: " + buffer.capacity());

            System.out.println(buffer.get());
        }
    }

}
