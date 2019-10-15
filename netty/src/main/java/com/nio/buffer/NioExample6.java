package com.nio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Zyh on 2019/9/9.
 */
@SuppressWarnings("all")
public class NioExample6 {
    /**
     * 通过NIO读取文件涉及到三个步骤
     *  1 从FileInputStream获取到FileChannel对象
     *  2 创建Buffer
     *  3 将数据从Channel读取到Buffer中
     *
     *  绝对方法与相对方法的含义
     *  1 相对方法：limit的值与position的值会在操作时会被考虑到,即值会改变.
     *  2 绝对方法：值不会改变,根据buffer数组的索引来get或put.
     */

    public static void main(String[] args) throws Exception {
        FileInputStream inputStream =
                new FileInputStream("netty/NioExample6-input.txt");
        // 输出流的文件会自动生成
        FileOutputStream outputStream =
                new FileOutputStream("netty/NioExample6-output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        // 文件中包含的字节数为13,声明两个Buffer来查看不同的效果
        // ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // 使用死循环是因为buffer是固定大小的,而文件中到底有多少字节是不得而知的.write和read
        // 每次都会以buffer为单位进行操作,假设buffer为10字节,那么每次写或读都会操作10字节的
        // 数据.当buffer的大小大于文件中的字节数时,此时会将文件数全部读出或写入,当数据全部被读完
        // 时会返回-1.
        for (;;) {
            // 将buffer重置到初始化状态.如果不加clear的话由于position一直无法重置(此时position
            // = limit),当再调用flip写出时,position的位置重置为0,此时就会把以前读取过的数据继续
            // 写出.当position=limit以后,如果不进行重置,那么将无法继续读取.因为position的值不会
            // 大于limit,所以此时会重复打印之前的值.
            buffer.clear();

            // read每次读取到一个数据.
            int readByte = inputChannel.read(buffer);
            // 打印每次读取的byte个数
            System.out.println("每次写入的byte数 = " + readByte);
            // 当为-1时,表示数据读取完毕
            if (-1 == readByte) {
                break;
            }

            // 翻转
            buffer.flip();

            // 写出
            int writeByte = outputChannel.write(buffer);
            System.out.println("每次读出的Byte数" + writeByte);
        }

        // 关闭channel
        inputChannel.close();
        outputChannel.close();
    }
}
