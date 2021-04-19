package com.rccsilva.whatsapp.gateway.controllers;

import com.rccsilva.whatsapp.gateway.domain.Message;
import com.rccsilva.whatsapp.gateway.domain.SendMessageRequest;
import com.rccsilva.whatsapp.gateway.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ChatController {

    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat")
    public void send(final Message message) throws Exception {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        this.messageService.fromUser(new SendMessageRequest(
                message.getFrom(),
                message.getTo(),
                message.getText(),
                time
        ));
    }
}