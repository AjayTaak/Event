package com.example.event;

import java.util.Date;

public class Reviews {

    private String username;
    private String message;
    private Date time;

    public Reviews(String username, String message, Date time) {
        this.username = username;
        this.message = message;
        this.time = time;
    }

    public Reviews() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
