package com.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Zyh on 2019/9/10.
 */
public class NioExample11 {

    /**
     * 内存映射文件:将文件系统（硬盘）中的文件映射到堆外内存中的某一块区域,进程可以直接操作这块内存区域来修改文件内容.
     * 对这块直接内存的数据进行的修改会由OS去完成.
     */
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("netty/NioExample11.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 获得文件系统的文件的内存映射
         * 构造方法参数
         *  文件映射的模式 - 读,写,可读可写
         *  映射起始位置
         *  映射的字节数
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        // 修改内存映射文件
        mappedByteBuffer.put(0, (byte)'a');
        mappedByteBuffer.put(3, (byte)'a');

        randomAccessFile.close();
    }
}
