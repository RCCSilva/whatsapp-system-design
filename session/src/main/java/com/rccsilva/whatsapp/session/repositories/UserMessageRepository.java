package com.rccsilva.whatsapp.session.repositories;

import com.rccsilva.whatsapp.session.domain.UserMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMessageRepository extends MongoRepository<UserMessage, String> {
    Optional<UserMessage> findByToUserId(String toUserId);
}
