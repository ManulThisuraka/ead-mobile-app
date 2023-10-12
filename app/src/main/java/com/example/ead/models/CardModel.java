package com.example.ead.models;

public class CardModel {

    private String id;
    private String trainScheduleid;
    private String nic;
    private String createdAt;
    private String updatedAt;
    private String date;
    private int count;
    private int status;
    private String from;
    private String time;
    private String to;

    public CardModel() {
    }

    public CardModel(String id, String trainScheduleid, String nic, String createdAt, String updatedAt, String date, int count, int status, String from, String time, String to) {
        this.id = id;
        this.trainScheduleid = trainScheduleid;
        this.nic = nic;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.date = date;
        this.count = count;
        this.status = status;
        this.from = from;
        this.time = time;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainScheduleid() {
        return trainScheduleid;
    }

    public void setTrainScheduleid(String trainScheduleid) {
        this.trainScheduleid = trainScheduleid;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
