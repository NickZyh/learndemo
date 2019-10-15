package com.nio.buffer;

import java.nio.ByteBuffer;

/**
 * Created by Zyh on 2019/9/10.
 */
public class NioExample8 {

    public static void main(String[] args) {
        /**
         * 分片buffer：将buffer切割,获取原buffer的一个快照生成出来的新的buffer和原来的
         * buffer是同一份数据,对新buffer的修改会在老buffer中显示出来
         */
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        // 设置起始指针为2
        buffer.position(2);
        // limit设置为6
        buffer.limit(6);

        // slice()会返回position与limit中间的数据(包含起始值,不包含终止值)
        ByteBuffer sliceBuffer = buffer.slice();
        // 4
        System.out.println("newBuffer capacity:" + sliceBuffer.capacity());

        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            System.out.println(sliceBuffer.get());
        }
    }
}
