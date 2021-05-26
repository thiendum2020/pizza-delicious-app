package com.example.pizzadelicious.Models;

public class Bill {
    private String id;
    private String prices;
    private String note;
    private String date;
    private User user;

    public Bill() {
    }

    public Bill(String id, String prices, String note, String date, User user) {
        this.id = id;
        this.prices = prices;
        this.note = note;
        this.date = date;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
