package com.rccsilva.whatsapp.session.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccsilva.whatsapp.session.domain.UserGateway;
import com.rccsilva.whatsapp.session.services.UserGatewayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserGatewayListener {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserGatewayService userGatewayService;

    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = {"session.gateway.set"})
    void handleMessage(String message) {
        logger.info("Received record to set user to gateway message: {}", message);
        try {
            UserGateway userGateway = objectMapper.readValue(message, UserGateway.class);
            this.userGatewayService.handleIncome(userGateway);
        } catch (JsonProcessingException jsonProcessingException) {
            logger.warn("Failed to deserialize message to UserGateway", jsonProcessingException);
        }
    }
}
