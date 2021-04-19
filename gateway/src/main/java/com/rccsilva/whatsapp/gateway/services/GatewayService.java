package com.rccsilva.whatsapp.gateway.services;

import com.rccsilva.whatsapp.gateway.domain.UserGateway;
import com.rccsilva.whatsapp.gateway.publishers.UserGatewayPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

    @Value("${app.id}")
    private String appId;

    @Autowired
    private UserGatewayPublisher userGatewayPublisher;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void publishUserGateway(String user) {
        this.logger.info("Processing {} user to send to session", user);
        UserGateway userGateway = new UserGateway(user, this.appId);
        this.userGatewayPublisher.publish(userGateway);
    }
}
