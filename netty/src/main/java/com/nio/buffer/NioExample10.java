package com.nio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Zyh on 2019/9/10.
 */
@SuppressWarnings("all")
public class NioExample10 {
    /**
     * 堆内buffer - HeapByteBuffer
     *   Java对象,在JVM的堆中分配内存.JVM能够直接控制这块内存
     * 堆外buffer - DirectByteBuffer
     *   两部分构成.JVM堆中创建的DirectByteBuffer对象,以及通过unsafe包在堆外分配的内存,这一块内存是存
     *   放数据的.由于堆外内存不在JVM管控中,所以会通过DirectByteBuffer的顶级父类Buffer中long address
     *   来进行关联(放置在Buffer中是为了通过JNI方式调用方法时更加快速);long address表示的是内存的偏移量.
     *
     * 零拷贝
     *   外部和进程内存空间在交互时,会将数据拷贝到操作系统内核空间中的一块内存中,然后外部或者进程
     *   内存会从内核空间中读取数据,并不会直接交互.在NIO中,如果使用堆外内存(分配在内核中),那么就
     *   不需要再进行拷贝,这样效率就会提高.
     *
     * 操作系统不直接操作堆内内存的原因
     *   内核态下,OS能够访问任意一块内存,但是如果OS操作堆内内存时,JVM发生了GC回收,这个时候数据就会混乱.但是
     *   如果不进行GC时就会抛出OOM.所以OS会具有一个拷贝的过程,因为相对于外设的IO效率,拷贝的效率显然更高.另外,
     *   拷贝过程中是不会发生GC的.
     *
     * 堆外内存的回收
     *   JVM堆中与堆外内存进行了关联,当DirectByteBuffer被回收时,会通过JNI的方式将堆外的内存给回收.所以不会发生
     *   内存泄漏的情况.Netty中的ByteBuf采用的就是零拷贝的方式.
     */
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream =
                new FileInputStream("netty/NioExample10-input.txt");
        FileOutputStream outputStream =
                new FileOutputStream("netty/NioExample10-output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        // 申请堆内内存 - HeapByteBuffer
        ByteBuffer heapBuffer = ByteBuffer.allocate(512);
        // 申请堆外内存 - DirectByteBuffer --> 零拷贝
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(512);

        for (;;) {
            directBuffer.clear();

            if (-1 == inputChannel.read(directBuffer)) {
                break;
            }

            directBuffer.flip();

            outputChannel.write(directBuffer);
        }

        inputChannel.close();
        outputChannel.close();
    }
}
