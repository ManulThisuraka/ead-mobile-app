package com.example.ead.models;

public class CardModel {

    private String date;
    private String from;
    private String to;
    private int count;

    public CardModel() {
    }

    public CardModel(String date, String from, String to, int count) {
        this.date = date;
        this.from = from;
        this.to = to;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
