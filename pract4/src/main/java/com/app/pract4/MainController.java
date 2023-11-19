package com.app.pract4;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@Controller
public class MainController {

    @MessageMapping("/webs")
    @SendTo("/topic/messages")
    public Message message(Message inputMessage) throws Exception {
        return new Message(new Date().getTime(), HtmlUtils.htmlEscape(inputMessage.getContent()));
    }
}
