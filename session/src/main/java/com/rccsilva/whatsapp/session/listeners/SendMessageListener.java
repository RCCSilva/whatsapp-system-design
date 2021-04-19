package com.rccsilva.whatsapp.session.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccsilva.whatsapp.session.domain.SendMessageRequest;
import com.rccsilva.whatsapp.session.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SendMessageListener {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = {"session.message.create"})
    void handleMessage(String message) {
        logger.info("Received record to send message: {}", message);
        try {
            SendMessageRequest sendMessageRequest =
                    objectMapper.readValue(message, SendMessageRequest.class);
            this.messageService.send(sendMessageRequest);
        } catch (JsonProcessingException jsonProcessingException) {
            this.logger.warn("Failed to serialize SendMessageRequest to String", jsonProcessingException);
        }
    }
}
