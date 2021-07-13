package com.example.pizzadelicious.Models;

import java.util.ArrayList;

public class JSONResponseBill {
    private String status;
    private String msg;
    private ArrayList<Bill> data;

    public JSONResponseBill() {
    }

    public JSONResponseBill(String status, String msg, ArrayList<Bill> data) {
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
    public ArrayList<Bill> getData() {
        return data;
    }

    public void setData(ArrayList<Bill> data) {
        this.data = data;
    }
}
