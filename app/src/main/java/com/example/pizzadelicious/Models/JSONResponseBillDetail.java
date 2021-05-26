package com.example.pizzadelicious.Models;

public class JSONResponseBillDetail {
    private String status;
    private BillDetail[] data;

    public JSONResponseBillDetail() {
    }

    public JSONResponseBillDetail(String status, BillDetail[] data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BillDetail[] getData() {
        return data;
    }

    public void setData(BillDetail[] data) {
        this.data = data;
    }
}
