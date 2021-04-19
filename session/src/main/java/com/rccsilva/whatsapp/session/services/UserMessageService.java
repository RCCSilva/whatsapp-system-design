package com.rccsilva.whatsapp.session.services;

import com.rccsilva.whatsapp.session.domain.SendMessageRequest;
import com.rccsilva.whatsapp.session.domain.UserMessage;
import com.rccsilva.whatsapp.session.repositories.UserMessageRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMessageService {

    @Autowired
    private UserMessageRepository userMessageRepository;

    public void pushMessage(@NotNull SendMessageRequest messageRequest) {
        UserMessage userMessage =
                userMessageRepository.findByToUserId(messageRequest.getTo()).orElse(
                        new UserMessage(messageRequest.getTo())
                );

        userMessage.pushMessage(messageRequest);

        userMessageRepository.save(userMessage);
    }
}
