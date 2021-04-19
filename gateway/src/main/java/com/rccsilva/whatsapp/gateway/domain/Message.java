package com.rccsilva.whatsapp.gateway.domain;

public class Message {

    private String from;
    private String to;
    private String text;

    public String getText() {
        return text;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() { return to; }
}
