package com.reactors.masterandslave;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("all")
public class TCPReactor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector; // mainReactor用的selector

    public TCPReactor(int port) throws IOException {
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(port));

        // 监听ServerSocketChannel并绑定事件,同时定义处理器
        SelectionKey sk = ssc.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor(ssc));
    }

    /**
     * mainReactor执行逻辑.
     */
    public void run() {
        while (!Thread.interrupted()) {
            System.out.println("mainReactor waiting for new event on port: " + ssc.socket().getLocalPort() + "...");

            try {
                if (selector.select() == 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();
            while (it.hasNext()) {
                dispatch(it.next());
                it.remove();
            }
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable r = (Runnable) (key.attachment());
        if (r != null) {
            // 交给subReactor执行
            r.run();
        }
    }
}