package com.example.pizzadelicious.Models;

public class BillDetail {
    private String id;
    private String quantity;
    private String prices;
    private Product product;
    private Bill bill;

    public BillDetail() {
    }

    public BillDetail(String id, String quantity, String prices, Product product, Bill bill) {
        this.id = id;
        this.quantity = quantity;
        this.prices = prices;
        this.product = product;
        this.bill = bill;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
