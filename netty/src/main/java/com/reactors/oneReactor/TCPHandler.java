package com.reactors.oneReactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * SocketChannel发生OP_READ事件的处理器
 */
@SuppressWarnings("all")
public class TCPHandler implements Runnable {
    private final SelectionKey sk;
    private final SocketChannel sc;

    public TCPHandler(SelectionKey sk, SocketChannel sc) {
        this.sk = sk;
        this.sc = sc;
    }

    /**
     * 处理过程
     */
    public void run() {
        try {
            // 读取数据
            read();
        } catch (IOException e) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
        }
    }

    private synchronized void read() throws IOException {
        // 构建Buffer
        byte[] arr = new byte[1024];
        ByteBuffer buf = ByteBuffer.wrap(arr);

        // socket从channel中读取数据,然后存放到buffer中,如果socket被关闭了则返回-1
        if(sc.read(buf) == -1) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
            return;
        }

        // 转成字符串
        String str = new String(arr, "UTF-8");
        if (!str.equals(" ")) {
            // 处理信息
            process(str);
            System.out.println(sc.socket().getRemoteSocketAddress().toString() + " > " + str);
        }
    }

    void process(String str) {
        try {
            // 用线程睡眠来模拟处理字符串
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 关闭资源
    private void closeChannel() {
        try {
            // 取消selector中的SelectionKey的监听
            sk.cancel();
            // 关闭socket
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}