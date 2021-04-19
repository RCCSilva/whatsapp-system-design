package com.rccsilva.whatsapp.session.domain;

public class UserGateway {
    final private String userId;
    final private String gateway;

    public UserGateway(String userId, String gateway) {
        this.userId = userId;
        this.gateway = gateway;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getGateway() {
        return this.gateway;
    }

    @Override
    public String toString() {
        return "UserGateway(userId=" + this.userId + ", gateway=" + this.gateway + ")";
    }
}
