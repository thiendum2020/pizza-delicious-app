package com.example.pizzadelicious.Models;

import java.util.ArrayList;

public class JSONResponseProduct {
    private String status;
    private String msg;
    private ArrayList<Product> data;

    public JSONResponseProduct() {
    }

    public JSONResponseProduct(String status, String msg,  ArrayList<Product> data) {
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

    public  ArrayList<Product> getData() {
        return data;
    }

    public void setData( ArrayList<Product> data) {
        this.data = data;
    }
}