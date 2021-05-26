package com.example.pizzadelicious.Models;

public class Product {
    private String id;
    private String name;
    private String price;
    private String type_id;
    private String image;

    public Product() {
    }

    public Product(String id, String name, String price, String type_id, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type_id = type_id;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
