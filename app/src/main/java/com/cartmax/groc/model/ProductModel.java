package com.cartmax.groc.model;

public class ProductModel {
    String Id, Name, Category, StoreID, image;
    double Price;

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    int Stock;

    public ProductModel(String name, String category, String storeID, double price, int Stock, String image) {
        Name = name;
        Category = category;
        StoreID = storeID;
        Price = price;
        this.Stock = Stock;
        this.image = image;
    }

    public ProductModel() {
    }

    public ProductModel(String id, String name, String category, String storeID, double price, int Stock, String imageURL) {
        Id = id;
        Name = name;
        Category = category;
        StoreID = storeID;
        Price = price;
        this.Stock = Stock;
        this.image = imageURL;
    }

    public String getimage() {
        return image;
    }

    public void setimage(String imageURL) {
        this.image = imageURL;
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
