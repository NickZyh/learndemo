package com.nio.selector;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Zyh on 2019/9/12.
 */
@SuppressWarnings("all")
@Slf4j
public class NioExample1 {
    /**
     * 阻塞与同步的概念
     * 一般来说，一个输入操作通常包括两个不同阶段：
     *      1）等待数据准备好；
     *      2）从内核向进程复制数据。
     * 是否同步的判断依据是:是否针对的是整个过程,也就是2个阶段,是否有阻塞。
     * 是否阻塞的判断依据是:按程序（线程）等待消息通知时的状态角度来说的,也就是主要是针对第一阶段来说。
     *      如果是等待内核复制数据的话则不算阻塞.
     */

    public static void main(String[] args) throws Exception {
        // 分配五个端口,一个线程监听五个端口
        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        // 创建一个selector
        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            // 为每个端口都打开一个面向流的Channel管道 - ServerSocketChannel
            ServerSocketChannel serverSocketChannel = serverSocketChannel = ServerSocketChannel.open();

            // 返回一个与ServerSocketChannel关联的一个ServerSocket对象
            ServerSocket serverSocket = serverSocketChannel.socket();
            // 将serversocket与端口进行绑定
            serverSocket.bind(new InetSocketAddress(ports[i]));

            // 设置为非阻塞,否则会报错IllegalBlockingModeException;并且一定要在注册到selector之前
            serverSocketChannel.configureBlocking(false);

            // 将当前ServerSocketChannel注册到selector中,并且选择接收事件,返回注册的SelectionKey
            // SelectionKey代表着一个注册到selector上的一个选择通道
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            log.info("监听端口：{}" + ports[i]);
        }

        for (;;) {
            // 阻塞方法,当有事件发生时则返回;返回值是发生的事件的数量(SelectionKey的数量)
            int eventNumber = selector.select();
            log.info("事件发生数：{}", eventNumber);

            // 返回发生事件的SelectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                // 获取SelectionKey,开始准备处理
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()) {  // 当SelectionKey中的是接收事件,此时一定是ServerSocketChannel产生的事件
                    // 获取SelectionKey中注册的channel,接收事件一定是ServerSocketChannel,所以进行强转
                    // 返回值SelectableChannel是所有channel的抽象
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    // 响应管道的事件,创建新的SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 设置为非阻塞
                    socketChannel.configureBlocking(false);

                    // 将新创建的socketChannel也注册到selector中进行监听
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    
                    log.info("接收并以监听客户端连接：{}", socketChannel);
                } else if (selectionKey.isReadable()) {  // 当SelectionKey中的是读事件,此时一定是SocketChannel产生的事件
                    // 获取发生读事件的SocketChannel
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    int bytesRead = 0;
                    int readBytes = 0;
                    for (;;) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        // 重置标记位到初始化状态
                        byteBuffer.clear();

                        readBytes = socketChannel.read(byteBuffer);
                        log.info("读取到的字节数：{}", readBytes);
                        if (readBytes <= 0) {
                            // 问题：0和-1分别是什么状态
                            log.info("读取完毕：{}", readBytes);
                            break;
                        }

                        byteBuffer.flip();

                        socketChannel.write(byteBuffer);
                        bytesRead += readBytes;
                    }
                    log.info("读取结束：{}字节数 - 来自于：{}", bytesRead, socketChannel);
                }
            }
            // 移除已经处理过的selectionKey,否则selectionKey会一直留在集合中
            iterator.remove();
            log.info("事件被移除");
        }
    }
}