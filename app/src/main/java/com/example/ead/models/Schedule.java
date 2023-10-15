package com.example.ead.models;

public class Schedule {
    private String _id;
    private String scheduleDate;
    private String departure;
    private String destination;
    private String startTime;
    private int availableSeatCount;

    public Schedule() {}

    public Schedule(String _id, String scheduleDate, String departure, String destination, String startTime, int availableSeatCount) {
        this._id = _id;
        this.scheduleDate = scheduleDate;
        this.departure = departure;
        this.destination = destination;
        this.startTime = startTime;
        this.availableSeatCount = availableSeatCount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getAvailableSeatCount() {
        return availableSeatCount;
    }

    public void setAvailableSeatCount(int availableSeatCount) {
        this.availableSeatCount = availableSeatCount;
    }
}
