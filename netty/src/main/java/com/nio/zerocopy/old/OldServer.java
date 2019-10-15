package com.nio.zerocopy.old;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Zyh on 2019/9/22.
 * <p>传统IO的socket</p>
 */
@SuppressWarnings("all")
@Slf4j
public class OldServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8899);

        while (true) {
            Socket socket = serverSocket.accept();

            // socket中是以流的形式传输数据
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            byte[] bytes = new byte[4096];

            while (true) {
                int readCount = dataInputStream.read(bytes, 0, bytes.length);

                // 当数据读取完毕时,返回-1
                if (readCount == -1) {
                    break;
                }
            }
        }
    }
}
