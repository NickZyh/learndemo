package com.learnnote.springwebsocket.controller;

import com.learnnote.springwebsocket.model.Greeting;
import com.learnnote.springwebsocket.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * @Author nick.zhou
 * @Date 2019/7/15 14:09
 * @Description <p>TODO</p>
 **/
@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        while(true) {
            return new Greeting("hello" + HtmlUtils.htmlEscape(message.getName()));
        }
    }
}
