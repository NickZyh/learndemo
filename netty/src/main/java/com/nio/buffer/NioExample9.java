package com.nio.buffer;

import java.nio.ByteBuffer;

/**
 * Created by Zyh on 2019/9/10.
 */
public class NioExample9 {

    public static void main(String[] args) {
        /**
         * 只读buffer：只能读,不能写
         */
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }

        // 当将buffer设置为只读后,就无法再修改
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        // HeapByteBufferR - 只读的HeapBuffer
        // class java.nio.HeapByteBufferR
        System.out.println(readOnlyBuffer.getClass());
        // java.nio.HeapByteBufferR
        System.out.println(readOnlyBuffer.getClass().getName());

        // 重置一下position,准备放入元素
        readOnlyBuffer.position(0);
        // 尝试往readOnlyBuffer中放入元素时直接抛出ReadOnlyBufferException
        readOnlyBuffer.put((byte)1);
    }
}
