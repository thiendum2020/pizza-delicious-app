package com.example.pizzadelicious.Models;

public class JSONResponseProduct {
    private String status;
    private Product[] data;

    public JSONResponseProduct() {
    }

    public JSONResponseProduct(String status, Product[] data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product[] getData() {
        return data;
    }

    public void setData(Product[] data) {
        this.data = data;
    }
}