package com.chatui;

public class Message {

    private String message = null;
    private String sendertype = null;
    private String time = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSendertype() {
        return sendertype;
    }

    public void setSendertype(String sender) {
        sendertype = sender;
    }
}