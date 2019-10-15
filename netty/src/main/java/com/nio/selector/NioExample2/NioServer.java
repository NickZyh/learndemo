package com.nio.selector.NioExample2;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Zyh on 2019/9/13.
 */
@SuppressWarnings("all")
@Slf4j
public class NioServer {

    private static final Map<String, SocketChannel> SOCKETCHANNEL_MAP = new HashMap<>();

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        ServerSocket serverSocket = serverSocketChannel.socket();
        // 在逻辑上,socket位于最底层,所以是socket与端口绑定
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();

        // selector监听的是channel,不是channel
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        log.info("服务器开始监听");
        while(true) {
            // 开始监听
            int eventsNumber = selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            // 遍历
            selectionKeys.forEach(selectionKey -> {
                final SocketChannel client;

                try {
                    // 接收事件
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        client = server.accept();

                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);

                        String key = "【" + UUID.randomUUID().toString() + "】";
                        SOCKETCHANNEL_MAP.put(key, client);
                    // 读事件
                    } else if (selectionKey.isReadable()) {
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        readBuffer.clear();

                        client = (SocketChannel) selectionKey.channel();

                        int count = client.read(readBuffer);
                        if (count > 0) {
                            // 将ByteBuffer的数据转成字符串
                            Charset charset = Charset.forName("utf-8");
                            String receivedMessage = new String(readBuffer.array(), 0, count);
                            log.info("服务端：【{}】{}", client, receivedMessage);

                            // readBuffer.flip();

                            String currentClientKey = null;
                            for (Map.Entry<String, SocketChannel> entry : SOCKETCHANNEL_MAP.entrySet()) {
                                if (entry.getValue().equals(client)) {
                                    currentClientKey = entry.getKey();
                                    break;
                                }
                            }

                            for (Map.Entry<String, SocketChannel> entry : SOCKETCHANNEL_MAP.entrySet()) {
                                SocketChannel value = entry.getValue();
                                if (value != client) {
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put((currentClientKey + receivedMessage).getBytes());
                                    writeBuffer.flip();
                                    value.write(writeBuffer);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // 清空集合,否则会报空指针异常,因为已经处理完毕了,下次处理就不存在了
            selectionKeys.clear();
        }
    }
}