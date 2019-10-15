package com.nio.zerocopy.old;

import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Zyh on 2019/9/22.
 */
@SuppressWarnings("all")
@Slf4j
public class OldClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8899);

        String fileName = "D://我的资源/我的资源.rar";

        InputStream inputStream = new FileInputStream(fileName);
        // 两次拷贝 1 磁盘->内核 2 内核到用户空间
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readCount;
        // 发送总字节数
        long total = 0;

        long startTime = System.currentTimeMillis();

        while((readCount = inputStream.read(buffer)) >= 0) {
            total += readCount;
            // 两次拷贝 1 用户空间到内核空间 2 内核 -> socket
            dataOutputStream.write(buffer);
        }

        log.info("发送总字节数：{} 耗时：{}ms", total, System.currentTimeMillis() - startTime);

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
