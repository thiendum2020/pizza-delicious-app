package com.example.pizzadelicious.Models;

public class JSONResponseAccounts {
    private String status;
    private User[] data;

    public JSONResponseAccounts() {
    }

    public JSONResponseAccounts(String status, User[] data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User[] getData() {
        return data;
    }

    public void setData(User[] data) {
        this.data = data;
    }
}