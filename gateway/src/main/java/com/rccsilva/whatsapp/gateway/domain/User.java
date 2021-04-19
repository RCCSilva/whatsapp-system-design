package com.rccsilva.whatsapp.gateway.domain;

import javax.security.auth.Subject;
import java.security.Principal;

public class User implements Principal {
    private final String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
