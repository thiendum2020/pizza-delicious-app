package com.example.pizzadelicious.Models;

public class JSONResponseUser {
    private String status;
    private User data;

    public JSONResponseUser() {
    }

    public JSONResponseUser(String status, User data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
