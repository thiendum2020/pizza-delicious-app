package com.example.pizzadelicious.Models;

import java.util.ArrayList;

public class JSONResponseBillDetail {
    private String status;
    private String msg;
    private ArrayList<BillDetail> data;

    public JSONResponseBillDetail() {
    }

    public JSONResponseBillDetail(String status,  String msg, ArrayList<BillDetail> data) {
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

    public  ArrayList<BillDetail> getData() {
        return data;
    }

    public void setData( ArrayList<BillDetail> data) {
        this.data = data;
    }
}
