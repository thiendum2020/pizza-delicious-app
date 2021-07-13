package com.example.pizzadelicious.Models;

import java.util.ArrayList;

public class JSONResponseAccounts {
    private String status;
    private String msg;
    private ArrayList<User> data;

    public JSONResponseAccounts() {
    }

    public JSONResponseAccounts(String status, String msg, ArrayList<User> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public ArrayList<User> getData() {
        return data;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }
}