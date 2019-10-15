package com.reactors.manyReactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 这个类就是和单Reactor单线程有区别的类。当SocketChannel已经被创建出来后,将socket中的数据读取出来,然后
 * 交给线程池,利用多线程进行处理。
 *
 * 至于为什么Socket不能用多线程处理,原因很简单,因为只有一个SocketHandler(TCPHandler),所以只能一个一个的
 * 将Socket数据读取出来,和TCPReactor是一样的道理,如果多个线程一起去读取Socket中的数据,即读取同一个buffer,
 * 那么由于多个线程在移动buffer的指针,所以当然会出问题。
 */
@SuppressWarnings("all")
public class TCPHandler implements Runnable {
    private final SelectionKey sk;
    private final SocketChannel sc;
    private final ThreadPoolExecutor pool;
    // 定义不同状态的处理器,这是扩展点,因为一个socket可能会对应多个事件,而不能让多个事件
    // 的handler都在当前handler中以方法表示(类似于oneReactor的send()方法,实际上这应该是
    // 需要定义一个单独的handler类来处理的)
    Handler handler;

    private static final int CORE_THREAD_COUNTING = 10;
    private static final int MAXIMUM_THREAD_COUNTING = 10;
    private static final int MAX_QUEUE_SIZE = 100;

    public TCPHandler(SelectionKey sk, SocketChannel sc) {
        this.sk = sk;
        this.sc = sc;
        this.pool = new ThreadPoolExecutor(CORE_THREAD_COUNTING, MAXIMUM_THREAD_COUNTING,
                10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(MAX_QUEUE_SIZE));

        // socket的初始化状态一定是read,因为是由读事件触发
        this.handler = new ReadHandler();
    }

    public void run() {
        try {
            // 根据不同的事件,绑定不同的handler,利用不同的handler去处理;相比于单Reactor单线程模型
            // 这是一个扩展。因为之前的我们假定一个Socket只会产生一个事件 - READ事件.但是有可能的是
            // 一个Socket也可能会绑定上多个事件,这个绑定时机是在代码的执行过程中进行绑定的.比如说Redis
            // 中当代码执行完后会修改为写事件,然后向客户端写数据。所以这里就是定义扩展点
            handler.handle(this, sk, sc, pool);
        } catch (IOException e) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
        }
    }

    public void closeChannel() {
        try {
            sk.cancel();
            sc.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}