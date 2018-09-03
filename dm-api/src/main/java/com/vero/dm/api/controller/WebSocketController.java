package com.vero.dm.api.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.vero.dm.model.messages.InputMessage;
import com.vero.dm.model.messages.OutputMessage;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:35 2018/8/12.
 * @since data-mining-platform
 */

@Controller
public class WebSocketController
{
    private SimpMessagingTemplate template;

    @Autowired
    public void setTemplate(SimpMessagingTemplate template)
    {
        this.template = template;
    }

    // 接收客户端"/app/chat"的消息，并发送给所有订阅了"/topic/messages"的用户
    @MessageMapping("/app/chat")
    @SendTo("/topic/messages")
    public OutputMessage receiveAndSend(@Payload InputMessage inputMessage)
        throws Exception
    {
        System.out.println("get message (" + inputMessage.getText() + ") from client!");
        System.out.println("send messages to all subscribers!");
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(inputMessage.getFrom(), inputMessage.getText(), time);
    }

    // 或者直接从服务端发送消息给指定客户端
    @MessageMapping("/chat_user")
    public void sendToSpecifiedUser(@Payload InputMessage inputMessage,
                                    SimpMessageHeaderAccessor headerAccessor)
        throws Exception
    {
        System.out.println("get message from client (" + inputMessage.getFrom() + ")");
        System.out.println("send messages to the specified subscriber!");
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        this.template.convertAndSend("/topic/" + inputMessage.getFrom(),
            new OutputMessage(inputMessage.getFrom(), inputMessage.getText(), time));
    }
}
