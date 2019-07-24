package com.learnnote.springwebsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Author nick.zhou
 * @Date 2019/7/15 14:14
 * @Description <p>配置STOMP协议</p>
 **/
@Configuration
@EnableWebSocketMessageBroker // 表示不仅配置了 WebSocket,还配置了基于代理的 STOMP 消息；
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * configure the message broker - 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 启动一个简单的基于内存的消息代理者来将服务端发出的message发送给前缀为/topic的客户端目的地,这也说明客户端需要定义以/topic为前端的地址
        // 可配置多个
        registry.enableSimpleBroker("/topic");
        // 为映射到使用@MessageMapping注解标注的方法的消息指定了/apps前缀指定了一个
        // 定义了服务端接收地址的前缀，也即客户端给服务端发消息的地址前缀
        // 可配置多个
        registry.setApplicationDestinationPrefixes("/apps");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 添加一个端点,这个端点可以理解为server.context-path;客户端就可以通过这个端点来进行连接；
        // withSockJS作用是添加SockJS支持;SockJS的作用就是如果浏览器支持WebSocket,则使用WebSocket,不能使用则用轮询
        registry.addEndpoint("/hello-websocket").withSockJS();
    }
}
