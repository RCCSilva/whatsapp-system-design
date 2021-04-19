package com.rccsilva.whatsapp.session.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccsilva.whatsapp.session.domain.SendMessageRequest;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Component;

@Component
public class SendMessagePublisher {

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    final private String topic = "gateway.appId.message.send";

    public RequestReplyFuture<String, String, String> publish(
        String gateway,
        SendMessageRequest sendMessageRequest
    ) {
        this.logger.info("Sending {} to gateway {}", sendMessageRequest, gateway);
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(
                topic.replace("appId", gateway),
                objectMapper.writeValueAsString(sendMessageRequest)
            );
            return this.kafkaTemplate
                .sendAndReceive(record);

        } catch (JsonProcessingException jsonProcessingException) {
            this.logger.warn("Failed to serialize SendMessageRequest to String", jsonProcessingException);
            jsonProcessingException.printStackTrace();
        }
        return null;
    }
}
