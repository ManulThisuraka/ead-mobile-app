package com.example.ead.models;

public class Reservation {

    private String date;
    private String from;
    private String to;
    private String schedule;
    private String count;

    public Reservation() {}

    public Reservation(String name, String skills, String date, String role, String message) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.schedule = schedule;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
