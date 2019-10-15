package com.reactors.oneReactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * ServerSocketChannel发生OP_ACCEPT事件的处理器
 */
@SuppressWarnings("all")
public class Acceptor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector;

    public Acceptor(Selector selector, ServerSocketChannel ssc) {
        this.ssc=ssc;
        this.selector=selector;
    }

    /**
     * 处理方法体
     */
    public void run() {
        try {
            // 接收连接请求,生成一个SocketChannel,相当于BIO的Socket
            SocketChannel sc = ssc.accept();
            System.out.println(sc.socket().getRemoteSocketAddress().toString() + " is connected.");

            // 将SocketChannel设置为非阻塞
            sc.configureBlocking(false);

            // 将新创建的SocketChannel可能发生的OP_READ事件注册到selector中,由selector监听
            SelectionKey sk = sc.register(selector, SelectionKey.OP_READ);

            // 待整理
            selector.wakeup();

            // 给SocketChannel注册的OP_READ事件附加一个处理器
            sk.attach(new TCPHandler(sk, sc));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}