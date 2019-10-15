package com.reactors.manyReactor;
      
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 一种抽象,将同一个Socket可能对应多个handler的请求进行抽象
 * 原因：一个Socket会产生多个事件
 */
public interface Handler {

     void changeState(TCPHandler h);

     void handle(TCPHandler h, SelectionKey sk, SocketChannel sc,
                 ThreadPoolExecutor pool) throws IOException ;
}