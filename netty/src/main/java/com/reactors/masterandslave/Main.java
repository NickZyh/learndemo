package com.reactors.masterandslave;
      
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // 创建Reactor模型,建立Server,启动Server的Accept事件的监听
            TCPReactor reactor = new TCPReactor(1333);
            reactor.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}