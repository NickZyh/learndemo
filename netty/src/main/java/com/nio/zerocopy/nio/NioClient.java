package com.nio.zerocopy.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Zyh on 2019/9/22.
 */
@SuppressWarnings("all")
@Slf4j
public class NioClient {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));

        String fileName = "D://我的资源/我的资源.rar";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();


        /**
         * Many operating systems can transfer bytes directly from the
         * filesystem cache to the target channel without actually copying
         * them.
         */
        // 实际传递的字节数组
        long transfer = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        log.info("发送总字节数：{} 耗时：{}ms", transfer, System.currentTimeMillis() - startTime);

        fileChannel.close();
    }
}
