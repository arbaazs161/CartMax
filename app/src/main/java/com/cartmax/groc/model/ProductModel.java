package com.cartmax.groc.model;

public class ProductModel {
    String Id, Name, Category, StoreID;
    double Price;

    public ProductModel(String name, String category, String storeID, double price) {
        Name = name;
        Category = category;
        StoreID = storeID;
        Price = price;
    }

    public ProductModel() {
    }

    public ProductModel(String id, String name, String category, String storeID, double price) {
        Id = id;
        Name = name;
        Category = category;
        StoreID = storeID;
        Price = price;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
