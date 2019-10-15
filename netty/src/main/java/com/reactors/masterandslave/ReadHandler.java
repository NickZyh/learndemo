package com.reactors.masterandslave;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

@SuppressWarnings("all")
public class ReadHandler implements Handler {

    private SelectionKey sk;

    public void changeState(TCPHandler h) {
        h.setHandler(new WorkHandler());
    }

    public void handle(TCPHandler h, SelectionKey sk, SocketChannel sc,
                       ThreadPoolExecutor pool) throws IOException {
        // 将产生事件的SelectionKey赋值
        this.sk = sk;
        // 声明buffer
        byte[] arr = new byte[1024];
        ByteBuffer buf = ByteBuffer.wrap(arr);

        if(sc.read(buf) == -1) {
            System.out.println("[Warning!] A client has been closed.");
            h.closeChannel();
            return;
        }

        String str = new String(arr, "UTF-8");
        if (!str.equals(" ")) {
            // READING状态结束,使用WorkHandler处理
            h.setHandler(new WorkHandler());

            // 多线程异步处理任务
            pool.execute(new Task(h, str));
            System.out.println(sc.socket().getRemoteSocketAddress().toString() + " > " + str);
        }
    }

    // 执行任务
    class Task implements Runnable {
        TCPHandler h;
        String str;

        public Task(TCPHandler h, String str) {
            this.h = h;
            this.str=str;
        }

        public void run() {
            // 开始处理业务逻辑
            process(h, str);
        }

    }

    synchronized void process(TCPHandler h, String str) {
        // 开始处理业务逻辑
    }
}