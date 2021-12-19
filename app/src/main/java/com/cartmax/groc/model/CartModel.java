package com.cartmax.groc.model;

public class CartModel {
    String productId, Id, userId;
    int quant;
    double price;

    public CartModel(String productId, String id, String userId, int quant, double price) {
        this.productId = productId;
        Id = id;
        this.userId = userId;
        this.quant = quant;
        this.price = price;
    }

    public CartModel(String productId, String userId, int quant, double price) {
        this.productId = productId;
        this.userId = userId;
        this.quant = quant;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }
}
