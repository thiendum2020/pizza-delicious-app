package com.example.pizzadelicious.Models;

public class Product {
    private String id;
    private String name;
    private String price;
    private Type type;
    private String image;

    public Product() {
    }

    public Product(String id, String name, String price, Type type, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
