package com.rccsilva.whatsapp.gateway.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccsilva.whatsapp.gateway.domain.SendMessageRequest;
import com.rccsilva.whatsapp.gateway.publishers.SendMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SendMessagePublisher sendMessagePublisher;

    @Autowired
    private ObjectMapper objectMapper;

    public void toUser(SendMessageRequest sendMessageRequest) throws JsonProcessingException {
        this.simpMessagingTemplate
                .convertAndSend(
                    "/topic/messages/" + sendMessageRequest.getTo(),
                    objectMapper.writeValueAsString(sendMessageRequest)
                );
    }

    public void fromUser(SendMessageRequest message) {
        this.sendMessagePublisher.publish(message);
    }
}
