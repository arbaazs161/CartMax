package com.cartmax.groc.model;

import java.lang.Object;
import android.os.Parcelable;

import com.google.type.LatLng;

import java.util.ArrayList;

public class StoreModel {
    String Name, Address, Id, Cover;
    String Location;
    ArrayList<String> Type;

    public StoreModel(String name, String address, String id, String cover, String location, ArrayList<String> type) {
        Name = name;
        Address = address;
        Id = id;
        Cover = cover;
        Location = location;
        Type = type;
    }

    public StoreModel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public ArrayList<String> getType() {
        return Type;
    }

    public void setType(ArrayList<String> type) {
        Type = type;
    }
}
