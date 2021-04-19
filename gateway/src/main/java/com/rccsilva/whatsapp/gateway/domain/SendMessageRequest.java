package com.rccsilva.whatsapp.gateway.domain;

public class SendMessageRequest {

    final private String from;
    final private String to;
    final private String text;
    final private String time;

    public SendMessageRequest(
            final String from,
            final String to,
            final String text,
            final String time) {

        this.from = from;
        this.to = to;
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public String getFrom() { return from; }

    public String getTo() { return to; }

    public String getTime() { return time; }
}
