package com.example.covider.model;

public class NotificationMessage {

    private long timeMillis;
    private String title;
    private String message;

    public NotificationMessage() {

    }

    public NotificationMessage(long timeMillis, String title, String message) {
        this.timeMillis = timeMillis;
        this.title = title;
        this.message = message;
    }

    public long getTime() {
        return timeMillis;
    }

    public void setTime(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
