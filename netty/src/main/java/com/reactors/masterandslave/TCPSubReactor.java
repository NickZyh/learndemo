package com.reactors.masterandslave;
      
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 一个线程单独对应一个subReactor,所以不存在并发安全问题
 */
@SuppressWarnings("all")
public class TCPSubReactor implements Runnable {
    private final ServerSocketChannel ssc;
    private final Selector selector;
    private boolean restart = false;
    // subReactor的编号
    private final int subReactorNum;

    public TCPSubReactor(Selector selector, ServerSocketChannel ssc, int subReactorNum) {
        this.ssc = ssc;
        this.selector = selector;
        this.subReactorNum = subReactorNum;
    }

    public void run() {
        while (!Thread.interrupted()) {
            System.out.println("waiting for restart");
            // 是否开始执行
            while (!Thread.interrupted() && !restart) {
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
    }

    private void dispatch(SelectionKey key) {
        Runnable r = (Runnable) (key.attachment());
        if (r != null) {
            r.run();
        }
    }

    // 是否开始执行
    public void setRestart(boolean restart) {
        this.restart = restart;
    }
}