package com.learnnote.rabbitmq.demo2;

import java.io.IOException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 抽象rabbitmq连接通道类
 */
@SuppressWarnings("all")
public abstract class ConnectionChannel {

    //登录信息
    private static final String HOSTNAME = "localhost";
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "980601@Zhou";

    //消息服务请求URL
    public static final String CMS_HOST = "http://" + HOSTNAME + ":8080";
    // routingKey
    public static final String ROUTING_KEY = "YOU.SELF.KEY";

    // 连接
    protected Connection connection;
    // 连接通道
    protected Channel channel;
    // 连接通道路由地址
    protected String routingKey;

    // 交换机名称
    protected final static String EXCHANGE_NAME = "cms";

    // 构造方法; 接收一个路由地址参数
    public ConnectionChannel(String routingKey) throws Exception {
        // 配置连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOSTNAME);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        //rabbitmq默认端口
        factory.setPort(5672);
        // 通俗的理解就是分配权限,每个VirtualHost相当于一个相对独立的RabbitMQ服务器，每个VirtualHost
        // 之间是相互隔离的。exchange、queue、message不能互通。vhost是rabbitmq分配权限的最小细粒度.可
        // 以为一个用户分配一个或多个可以访问的vhost的权限。多个user共同使用一个virtual host.
        // 默认就是都可以访问
        // 待整理：rabbitmq权限 https://blog.csdn.net/super_rd/article/details/71191851
        factory.setVirtualHost("/");
        // 建立连接
        connection = factory.newConnection();

        // rabbitmq不能基于connection通信,只能基于channel通信.channel是一个轻量级的连接,位于
        // connection内部,所有的操作都是基于channel来的.
        channel = connection.createChannel();
        /**
         * 定义exchange(基于channel)：生产者 -> 交换机(分发) -> Queue
         * 参数1：交换机名称
         * 参数2：交换机类型
         * 参数3：是否持久化，如果为true则服务器重启时不会丢失
         * 参数4：交换机在不被使用时是否删除
         * 参数5：交换机的其他属性
         */
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", false, false, null);
        // 指定exchange分发的路由规则
        this.routingKey = routingKey;
    }

    /**
     * 关闭channel和connection; 非必须，因为隐含是自动调用的。
     * @throws IOException
     */
    public void close() throws Exception {
        this.channel.close();
        this.connection.close();
    }
}
