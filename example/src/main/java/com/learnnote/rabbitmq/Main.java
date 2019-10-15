package com.learnnote.rabbitmq;

import com.learnnote.rabbitmq.demo1.Consumer;
import com.learnnote.rabbitmq.demo1.Producer;

/**
 * @Author Zyh
 * @Date 2019/8/25 20:07
 * @Description
 * @Note
 */
public class Main {
    public static void main(String[] args) {
        // demo01
        Producer producer = new Producer();
        producer.produce("生产者发送的信息");

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Consumer consumer = new Consumer();
        consumer.consumer();

        while(true) {

        }
    }
}
