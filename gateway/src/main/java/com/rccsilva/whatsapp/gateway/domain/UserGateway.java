package com.rccsilva.whatsapp.gateway.domain;

public class UserGateway {

    final private String userId;
    final private String gateway;

    public UserGateway(final String name, final String gateway) {
        this.userId = name;
        this.gateway = gateway;
    }

    public String getUserId() {
        return userId;
    }

    public String getGateway() {
        return gateway;
    }
}
