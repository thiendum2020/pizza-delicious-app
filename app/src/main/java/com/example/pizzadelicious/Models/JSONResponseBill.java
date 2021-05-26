package com.example.pizzadelicious.Models;

import java.util.ArrayList;

public class JSONResponseBill {
    private String status;
    private ArrayList<Bill> data;

    public JSONResponseBill() {
    }

    public JSONResponseBill(String status, ArrayList<Bill> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Bill> getData() {
        return data;
    }

    public void setData(ArrayList<Bill> data) {
        this.data = data;
    }
}
