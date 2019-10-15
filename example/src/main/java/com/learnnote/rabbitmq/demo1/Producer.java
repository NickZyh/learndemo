package com.learnnote.rabbitmq.demo1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jdk.nashorn.internal.objects.NativeUint8Array;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author Zyh
 * @Date 2019/8/25 19:47
 * @Description
 * @Note
 */
public class Producer {

    private static final String PASSWORD = "980601@Zhou";
    private static final String QUEUE_NAME = "demo_queue";

    public void produce(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        // 其他的连接信息使用默认值
        factory.setPassword(PASSWORD);

        // 确保资源能够被关闭
        try(Connection connection = factory.newConnection();
            // 轻量级Connection
            Channel channel = connection.createChannel()) {
            // 定义一个QUEUE,如果没有就创建一个
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 发送消息到mq中
            channel.basicPublish("", QUEUE_NAME, null,
                    message.getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
