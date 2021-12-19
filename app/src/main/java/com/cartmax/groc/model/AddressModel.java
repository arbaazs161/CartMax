package com.cartmax.groc.model;

public class AddressModel {
    String city, line, userID;
    long pincode;

    public AddressModel(String city, String line, String userID, long pincode) {
        this.city = city;
        this.line = line;
        this.userID = userID;
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getPincode() {
        return pincode;
    }

    public void setPincode(long pincode) {
        this.pincode = pincode;
    }
}
