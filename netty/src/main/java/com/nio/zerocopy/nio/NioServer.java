package com.nio.zerocopy.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Zyh on 2019/9/22.
 */
@SuppressWarnings("all")
@Slf4j
public class NioServer {

    public static void main(String[] args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(8899);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();

        // time-wait状态下可重用
        serverSocket.setReuseAddress(true);
        serverSocket.bind(address);

        ByteBuffer buffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();

            int readCount = 0;

            while (-1 != readCount) {
                readCount = socketChannel.read(buffer);

                // 连续读
                buffer.rewind();
            }
        }
    }
}
