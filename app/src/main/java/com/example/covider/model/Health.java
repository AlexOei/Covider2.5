package com.example.covider.model;

public class Health {

    private long timeMillis;
    private boolean status;

    public Health() {

    }
    public Health(long timeMillis, boolean status) {
        this.timeMillis = timeMillis;
        this.status = status;
    }

    public long getTime() {
        return timeMillis;
    }

    public void setTime(long time) {
        this.timeMillis = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
