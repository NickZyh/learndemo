package com.learnnote.rabbitmq.demo1;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author Zyh
 * @Date 2019/8/25 19:57
 * @Description
 * @Note
 */
public class Consumer {
    private static final String PASSWORD = "980601@Zhou";
    private static final String QUEUE_NAME = "demo_queue";

    public void consumer() {
        ConnectionFactory factory = new ConnectionFactory();
        // 其他的连接信息使用默认值
        factory.setPassword(PASSWORD);

        // 确保资源能够被关闭
        try(Connection connection = factory.newConnection();
            // 轻量级Connection
            Channel channel = connection.createChannel()) {
            // 定义一个QUEUE,如果没有就创建一个
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 消费到消息的回调
            DeliverCallback deliverCallback = (s, delivery) -> System.out.println("生产者发送的消息" + new String(delivery.getBody(), "UTF-8"));

            CancelCallback cancelCallback = s -> {
                System.out.println("111");
            };

            // 订阅QUEUE进行消费
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
