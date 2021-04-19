package com.rccsilva.whatsapp.gateway.publishers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccsilva.whatsapp.gateway.domain.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendMessagePublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    final private String topic = "session.message.create";

    public void publish(SendMessageRequest message) {
        try {
            this.kafkaTemplate.send(topic, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException jsonProcessingException) {
            this.logger.warn("Failed to serialize message to JSON", jsonProcessingException);
        } finally {
            this.kafkaTemplate.flush();
        }
    }
}
