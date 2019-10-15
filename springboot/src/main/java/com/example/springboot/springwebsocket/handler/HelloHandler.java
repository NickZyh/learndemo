package com.example.springboot.springwebsocket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @Author nick.zhou
 * @Date 2019/7/15 14:44
 * @Description <p>TODO</p>
 *      这个类是作为在WebSocket建立的各个阶段会回调的方法,有两种实现方式
 *          1 实现WebSocketHandler接口,但是要一次实现所有的方法
 *          2 继承AbstractWebSocketHandler抽象类,这个类实现了WebSocketHandler接口,相当于对接口包装了一层,不需要一次实现所有的方法
 *          3 也可以直接调用AbstractWebSocketHandler的子类TextWebSocketHandler或者BinaryWebSocketHandler,表示针对不同的消息内容进行不同处理
 **/
public class HelloHandler extends TextWebSocketHandler {

    /**
     * 当WebSocket连接创建成功时调用
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

    }

    /**
     * 当接收到前端消息时调用
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    /**
     * 当传输错误时调用
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    /**
     * 连接关闭之后调用
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
