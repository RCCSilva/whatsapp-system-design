package com.rccsilva.whatsapp.gateway.publishers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccsilva.whatsapp.gateway.domain.SendMessageRequest;
import com.rccsilva.whatsapp.gateway.domain.UserGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserGatewayPublisher {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    final private String topic = "session.gateway.set";

    public void publish(UserGateway userGateway) {
        try {
            this.kafkaTemplate.send(topic, objectMapper.writeValueAsString(userGateway));
        } catch (JsonProcessingException jsonProcessingException) {
            this.logger.warn("Failed to serialize message to JSON", jsonProcessingException);
        } finally {
            this.kafkaTemplate.flush();
        }
    }
}
