package com.example.event;

import java.util.Date;

public class Event {

    private String title;
    private String description;
    private Date startTime;
    private Date EndTime;

    public Event(String title, String description, Date startTime, Date EndTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.EndTime = EndTime;
    }

    public Event() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }
}
