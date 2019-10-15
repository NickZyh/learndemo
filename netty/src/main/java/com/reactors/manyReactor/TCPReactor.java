package com.reactors.manyReactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Reactor模型
 */
@SuppressWarnings("all")
public class TCPReactor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector;

    public TCPReactor(int port) throws IOException {
        // 开启selector
        selector = Selector.open();

        // 打开服务端的channel,相当于bio的ServerSocket
        ssc = ServerSocketChannel.open();

        // 将ServerSocketChannel绑定端口
        ssc.socket().bind(new InetSocketAddress(port));
        // 将ServerSocketChannel设置为非阻塞,这里一定是false,因为ServerSocketChannel只能为非阻塞
        ssc.configureBlocking(false);

        // 将ServerSocketChannel与连接事件绑定,并注册到selector,由selector来监控
        // 当有连接事件发生的时候selector就能够监听到
        // SelectionKey代表着注册到selector中的元素,这里是ServerSocketChannel
        SelectionKey sk = ssc.register(selector, SelectionKey.OP_ACCEPT);
        // 这里用于绑定一个对象到SelectionKey上。这个方法相当于封装一些资源到SelectionKey
        // 中,用于当事件发生时能够调用。当事件发生时我们能够获取到SelectionKey,然后可以从
        // SelectionKey中获取到绑定的对象,然后调用这个对象中的方法.
        sk.attach(new Acceptor(selector, ssc));
    }

    public void run() {
        // 死循环执行
        while (!Thread.interrupted()) {
            System.out.println("Waiting for new event on port: " + ssc.socket().getLocalPort() + "...");
            try {
                // 驱动事件的入口
                // select()是一个阻塞的方法,当selector有事件发生时才返回,返回值为事件发生个数
                // 待整理：select的bug
                if (selector.select() == 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 走到这里表示已经有事件发生了,那么就获取selector监听到的所有事件对应的SelectionKey
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            // 遍历事件
            Iterator<SelectionKey> it = selectedKeys.iterator();
            while (it.hasNext()) {
                // 获取到每个事件,然后分发
                dispatch((it.next()));
                // 当处理完毕后,移除对应的事件,表示处理完毕;这个地方要移除,否则会出现问题
                it.remove();
            }
        }
    }

    /**
     * 事件分发
     * 通过不同的附加对象来进行不同的操作,相当于不同的事件进行不同处理
     * 获取所有发生的事件,然后交给处理器处理
     * @param key
     */
    private void dispatch(SelectionKey key) {
        // 取出附加对象,不同的key可能附加不同对象,这个对象就可以理解为处理器
        //      ServerSocketChannel：new Acceptor(selector, ssc)
        Runnable r = (Runnable) (key.attachment());
        if (r != null) {
            // 开始处理事件,因为每个处理器的处理方法实际是被抽象出来的
            r.run();
        }
    }
}