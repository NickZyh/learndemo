package com.nio.buffer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Created by Zyh on 2019/9/10.
 * <p>
 *     buffer的scattering(分散)与Gathering(聚合)
 *     scattering(分散)：传递buffer时不仅仅可以传递一个buffer,还可以传递一个buffer数组;将一个channel中的数据
 *     使用多个buffer存储.
 *     Gathering(聚合)：将多个buffer的数据传给同一个channel进行处理.
 *
 *     使用场景：可以对一个串数据中的不同作用的数据分开存储在不同的byte中.
 * </p>
 */
public class NioExample12 {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        // 绑定服务器channel
        serverSocketChannel.socket().bind(address);

        int messageLength = 2 + 3 + 4;

        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);

        // 接收客户端连接,生成socket
        SocketChannel socketChannel = serverSocketChannel.accept();

        for (;;) {
            /**
             * scattering(分散)
             */
            int bytesRead = 0;
            // 当while循环结束之后说明被数组中的每一个buffer都被读满了
            while(bytesRead < messageLength) {
                // channel中的数据读入到ByteBuffer数组中,每次read要么就是将channel中的数据全部读取出来存储
                // 到byteBuffers中,要么就是bytesBuffer中的每个buffer都被写满了,并不是每次读一个然后写一个
                long read = socketChannel.read(byteBuffers);
                System.out.println(read);
                bytesRead += read;

                // map():返回一个原有流中所有元素执行完参数内的函数后的一个流.当前方法是将byteBuffer的信息给打印出来
                // forEach():遍历流中的每一个元素,并执行传入的函数
                Arrays.asList(byteBuffers).stream().map(
                        byteBuffer -> "position:" + byteBuffer.position()
                                + ",limit: " + byteBuffer.limit())
                        .forEach(System.out::println);
            }

            // 翻转,准备写回客户端
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());

            /**
             * Gathering(聚合)
             */
            long bytesWrite = 0;
            while(bytesWrite < messageLength) {
                // 将byteBuffers中的所有数据写回给客户端
                long write = socketChannel.write(byteBuffers);
                bytesWrite += write;
            }

            // 重置
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

            System.out.println("bytesRead:" + bytesRead + ",bytesWrite:" + bytesWrite
                    + "messageLength:" + messageLength);
        }
    }
}
