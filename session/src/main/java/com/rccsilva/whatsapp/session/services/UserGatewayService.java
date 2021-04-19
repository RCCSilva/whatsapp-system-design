package com.rccsilva.whatsapp.session.services;

import com.rccsilva.whatsapp.session.domain.SendMessageRequest;
import com.rccsilva.whatsapp.session.domain.UserGateway;
import com.rccsilva.whatsapp.session.domain.UserMessage;
import com.rccsilva.whatsapp.session.repositories.UserGatewayRepository;
import com.rccsilva.whatsapp.session.repositories.UserMessageRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGatewayService {

    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserGatewayRepository userGatewayRepository;

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Autowired
    private MessageService messageService;

    public void handleIncome(UserGateway userGateway) {
        logger.info("Saving UserGateway {}", userGateway);
        userGatewayRepository.save(userGateway);

        userMessageRepository
            .findByToUserId(userGateway.getUserId())
            .ifPresent(this::sendPersistedMessages);
    }

    public String get(String userId) {
        logger.info("Getting gateway of {}", userId);
        return userGatewayRepository.find(userId);
    }

    private void sendPersistedMessages(@NotNull UserMessage userMessage) {
        List<SendMessageRequest> messages = userMessage.getMessages();
        messages.forEach(message -> messageService.send(message) );

        this.userMessageRepository.deleteById(userMessage.getId());
    }
}
