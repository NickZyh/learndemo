package com.reactors.masterandslave;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public class WorkHandler implements Handler {

    public void changeState(TCPHandler h) {
        //h.setHandler(new WriteHandler());
    }

    public void handle(TCPHandler h, SelectionKey sk, SocketChannel sc,
                       ThreadPoolExecutor pool) throws IOException {
        // 开始进行处理
    }

}