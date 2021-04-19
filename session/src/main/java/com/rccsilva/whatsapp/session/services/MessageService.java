package com.rccsilva.whatsapp.session.services;

import com.rccsilva.whatsapp.session.domain.SendMessageRequest;
import com.rccsilva.whatsapp.session.publisher.SendMessagePublisher;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class MessageService {

    @Autowired
    private UserGatewayService userGatewayService;

    @Autowired
    private SendMessagePublisher sendMessagePublisher;

    @Autowired
    private UserMessageService userMessageService;

    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void send(SendMessageRequest message) {
        String gateway = userGatewayService.get(message.getTo());

        if (gateway == null) {
            saveMessageToBeDeliveredLater(message);
            return;
        }

        sendMessagePublisher
            .publish(gateway, message)
            .addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onFailure(@NotNull Throwable throwable) {
                    logger.info("Gateway failed acknowledged message", throwable);
                    saveMessageToBeDeliveredLater(message);
                }

                @Override
                public void onSuccess(ConsumerRecord<String, String> consumerRecord) {
                    logger.info("Gateway acknowledged message");
                }
            });
    }

    private void saveMessageToBeDeliveredLater(SendMessageRequest message) {
        logger.info("Saving message to be delivered later... {}", message);
        userMessageService.pushMessage(message);
    }
}
