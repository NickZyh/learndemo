package com.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author Zyh
 * @Date 2019/8/30 22:44
 * @Description
 * @Note
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    private static final String READER_IDLE = "读空闲";
    private static final String WRITER_IDLE = "写空闲";
    private static final String ALL_IDLE = "读写空闲";

    /**
     * 事件被触发时调用的handler
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = READER_IDLE;
                    break;
                case WRITER_IDLE:
                    eventType = WRITER_IDLE;
                    break;
                case ALL_IDLE:
                    eventType = ALL_IDLE;
                    break;
                default:
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "超时事件: " + eventType);
            ctx.channel().close();
        }
    }
}
