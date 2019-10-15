package com.reactors.oneReactor;
      
import java.io.IOException;

/**
 * 单线程的reactor模型
 */
public class Main {

    public static void main(String[] args) {
        try {
            // 初始化reactor模型
            TCPReactor reactor = new TCPReactor(1333);
            // 开始运行reactor模型,事件驱动的入口
            reactor.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}