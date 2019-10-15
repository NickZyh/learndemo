package com.nio.buffer;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Created by Zyh on 2019/9/6.
 */
public class NioExample1 {

    /**
     * java.io:Java.io中最为核心的概念就是流,面向流的编程.流就是一个信息的载体,byte[]数据在流
     * 中进行流动.一个流从本源上来看,要么是输入流,要么是输出流,不可能同时既是输入流,又是输出流.
     *
     * java.nio:以非阻塞的方式来处理IO操作;nio中有三个核心概念：
     *      1 selector-选择器
     *      2 channel-通道
     *          channel就能够理解成selector与buffer之间的一个通道.是一个可以向其写入数据或
     *          读取数据的对象;并且所有数据的读写都一定是从buffer来进行的,永远不会出现直接向
     *          Channel写入数据或是从channel读取数据的情况.
     *          由于channel是双向的,所以它能更好的反映出底层操作系统的真实情况;在Linux中,底层
     *          操作系统的通道就是双向的.
     *      3 buffer-缓冲区
     *          buffer底层实际上就是一块内存(数组),数据的读和写都是通过buffer来实现的,一个buffer
     *          既可以写又可以读.
     *          除了boolean类型外,Java中其他7种数据类型都有各自对应的buffer类型.
     *
     * nio中是面向块(block)或者是面向缓冲区(buffer)来编程的.一个selector运行在一个线程上,
     * 一个selector会监控多个channel,每个channel都会有一个buffer与之对应.nio中,事件是一
     * 个很重要的概念,当buffer产生对应的事件(读写)时,selector就会根据产生事件的buffer对应
     * 的channel对buffer进行相关操作.nio中所有的数据一定是从buffer来读取的,至于buffer中的
     * 数据从哪里读过来则是不确定的.
     *
     *
     */

    public static void main(String[] args) {
        // 生成一个IntBuffer,底层为一个byte[]数组,allocate为声明一个堆内内存HeapIntBuffer
        // 参数为byte[]的容量capacity
        IntBuffer buffer = IntBuffer.allocate(10);

        for(int i = 0; i < buffer.capacity(); i++) {
            // SecureRandom生成的随机数更随机,表示生成0 - 20以内的随机数
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        // 翻转
        buffer.flip();

        // 当buffer中还存在数据时,就读取并打印出来
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
