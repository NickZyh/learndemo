package com.reactors.masterandslave;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@SuppressWarnings("all")
public class Acceptor implements Runnable {
    private final ServerSocketChannel ssc;
    // 获取当前计算机CPU的核心数
    private final int cores = Runtime.getRuntime().availableProcessors();
    // 创建CPU核心数的Selector数组
    private final Selector[] selectors = new Selector[cores];
    // 当前正在执行的subReactor的number
    private int selIdx = 0;
    // subReactor線程
    private TCPSubReactor[] subReactors = new TCPSubReactor[cores];
    // 创建核心线程数个线程
    private Thread[] t = new Thread[cores];

    public Acceptor(ServerSocketChannel ssc) throws IOException {
        this.ssc = ssc;
        for (int i = 0; i < cores; i++) {
            // 创建subReactor并注册一个selector,并放入subReactors中
            subReactors[i] = new TCPSubReactor(Selector.open(), ssc, i);
            // 为每个subReactor单独创建一个线程执行
            t[i] = new Thread(subReactors[i]);
            // 启动subReactors
            t[i].start();
        }
    }

    /**
     * subReactor的执行逻辑,触发的时机就是发生Accept事件;因为mainReactor和subReactor监听的是一个
     * ServerSocketChannel;只不过mainReactor是专门负责响应事件,而subReactor是专门接收创建SocketChannel
     */
    public synchronized void run() {
        try {
            SocketChannel sc = ssc.accept();
            System.out.println(sc.socket().getRemoteSocketAddress().toString() + " is connected.");

            if (sc != null) {
                sc.configureBlocking(false);
                /**
                 * 通过修改不同subReactor所在的线程的selector,添加事件,从而达到共同处理的目的
                 */
                // 暂停当前执行的subReactor,这是因为需要将新创建的Socket加入当前subReactor的selector中
                subReactors[selIdx].setRestart(true);
                // 使一個阻塞住的selector操作立即返回(待整理)
                selectors[selIdx].wakeup();
                // 加入当前selector中
                SelectionKey sk = sc.register(selectors[selIdx], SelectionKey.OP_READ);
                // 相当于一个刷新操作,由于subReactor独有一个selector,所以这个操作相当于刷新一下当前
                // selector下一次的阻塞
                selectors[selIdx].wakeup();
                // 当前selector重新开始运行
                subReactors[selIdx].setRestart(false);
                // 給定key一個附加的TCPHandler對象
                sk.attach(new TCPHandler(sk, sc));
                // 轮流运行(待整理)
                if (++selIdx == selectors.length) {
                    selIdx = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}