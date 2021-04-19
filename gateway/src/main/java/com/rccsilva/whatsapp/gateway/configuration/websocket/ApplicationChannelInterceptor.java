package com.rccsilva.whatsapp.gateway.configuration.websocket;

import com.rccsilva.whatsapp.gateway.domain.User;
import com.rccsilva.whatsapp.gateway.services.GatewayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class ApplicationChannelInterceptor implements ChannelInterceptor {

    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GatewayService gatewayService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String user = accessor
                    .getNativeHeader("X-Authorization")
                    .get(0);
            logger.info("User: {}", user);
            if (user != null) {
                accessor.setUser(new User(user));
                gatewayService.publishUserGateway(user);
            }
        }
        return message;

    }
}
