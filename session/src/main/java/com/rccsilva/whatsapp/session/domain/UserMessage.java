package com.rccsilva.whatsapp.session.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Document("user_messages")
public class UserMessage {
    @Id
    private String id;
    @Field("to_user_id")
    @Indexed(unique = true)
    private String toUserId;
    private ArrayList<SendMessageRequest> messages;

    public UserMessage(String toUserId) {
        this.toUserId = toUserId;
        this.messages = new ArrayList<>();
    }

    public void pushMessage(SendMessageRequest message) {
        this.messages.add(message);
    }

    public String getId() { return id; }

    public String getToUserId() {
        return toUserId;
    }

    public ArrayList<SendMessageRequest> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<SendMessageRequest> messages) {
        this.messages = messages;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
}
