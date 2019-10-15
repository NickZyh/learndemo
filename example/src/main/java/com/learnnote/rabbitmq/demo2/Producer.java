package com.learnnote.rabbitmq.demo2;

import com.learnnote.rabbitmq.demo2.ConnectionChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Zyh
 * @Date 2019/8/25 17:41
 * @Description
 * @Note
 */
@Slf4j
@SuppressWarnings("all")
public class Producer extends ConnectionChannel {

    //持久化 队列 名称
    private String queueName;

    public Producer(String routingKey) throws Exception {
        super(routingKey);
        this.queueName = "queue_topic";
    }

    public Producer(String routingKey, String queueName) throws Exception {
        super(routingKey);
        this.queueName = queueName;
    }

    public void sendMessage(byte[] bodys) throws Exception{
        //声明一个队列 - 持久化
        channel.queueDeclare(queueName, true, false, false, null);
        //设置通道预取计数
        channel.basicQos(1);
        //将消息队列绑定到Exchange
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

        /**
         * 发送消息到队列中
         * 参数1：交换机exchange名字，若为空则使用默认的exchange[]
         * 参数2：routing key - 路由地址
         * 参数3：其他的属性
         * 参数4：消息体
         * RabbitMQ默认有一个exchange，叫default exchange，它用一个空字符串表示，它是
         * direct exchange类型，任何发往这个exchange的消息都会被路由到routing key的名
         * 字对应的队列上，如果没有对应的队列，则消息会被丢弃
         */
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, bodys);
        log.info("PDM消息发送成功 -- [ " + EXCHANGE_NAME + " ] - " + routingKey);
    }
}
