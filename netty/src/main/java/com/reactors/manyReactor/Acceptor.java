package com.reactors.manyReactor;

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
     * 处理方法体,这个方法体的作用就是接收到连接,然后组装Socket(注册绑定事件和对应的处理器)
     *
     * 这里不能用多线程处理的原因是假设此时ServerSocketChannel产生了事件,而这个处理器是多线程
     * 的,那么就有可能多个处理器一起去处理ServerSocketChannel中发过来的连接,那么就可能多个线程
     * 去争夺一个连接,也就是ssc.accept()这块代码会出问题.所以只能将Socket构造好之后,将Socket数据
     * 读取出来然后再进行多线程处理.
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

            selector.wakeup();

            // 给SocketChannel注册的OP_READ事件附加一个处理器
            sk.attach(new TCPHandler(sk, sc));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}