package com.rccsilva.whatsapp.gateway.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccsilva.whatsapp.gateway.domain.SendMessageRequest;
import com.rccsilva.whatsapp.gateway.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SendMessageListener {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ObjectMapper objectMapper;

    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = {"${kafka.topic.gateway.message.send}"})
    @SendTo
    Message<String> handleMessage(String message) throws JsonProcessingException {
        logger.info("Received message to send {}", message);
        this.messageService.toUser(objectMapper.readValue(message, SendMessageRequest.class));
        return MessageBuilder.withPayload("0").build();
    }
}
