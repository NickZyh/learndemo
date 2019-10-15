package com.nio.selector.NioExample2;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Zyh on 2019/9/16.
 */
@SuppressWarnings("all")
@Slf4j
public class NioClient {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            // 连接事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("localhost", 8899));

            for (;;) {
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey -> {
                    try {
                        SocketChannel client = (SocketChannel) selectionKey.channel();

                        // 连接事件
                        if (selectionKey.isConnectable()) {
                            // 连接是否处于连接过程当中
                            if (client.isConnectionPending()) {
                                // 手动触发完成连接
                                client.finishConnect();

                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());

                                writeBuffer.flip();
                                client.write(writeBuffer);

                                log.info("连接成功");

                                // 开启一个线程异步的等待键盘标准输入
                                ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                                executorService.submit(() -> {
                                    try {
                                        for (;;) {
                                            writeBuffer.clear();
                                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                                            String sendMessage = br.readLine();
                                            writeBuffer.put(sendMessage.getBytes(Charset.forName("utf-8")));

                                            writeBuffer.flip();
                                            client.write(writeBuffer);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                // 注册读取事件
                                client.register(selector, SelectionKey.OP_READ);
                            }
                        } else if (selectionKey.isReadable()) {  // 产生读事件
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            readBuffer.clear();

                            int readCount = client.read(readBuffer);
                            if (readCount > 0) {
                                String recievedMessage = new String(readBuffer.array(), 0, readCount);
                                System.out.println(recievedMessage);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
