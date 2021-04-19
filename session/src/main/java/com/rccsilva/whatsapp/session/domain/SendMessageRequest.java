package com.rccsilva.whatsapp.session.domain;

public class SendMessageRequest {

    final private String from;
    final private String to;
    final private String text;
    final private String time;

    public SendMessageRequest(String from, String to, String text, String time) {
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

    @Override
    public String toString() {
        return "SendMessageRequest(from=" + from + ", to=" + to + ", text=" + text + ", time=" + time + ")";
    }
}
