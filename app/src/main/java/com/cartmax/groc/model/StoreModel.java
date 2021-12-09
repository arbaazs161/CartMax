package com.cartmax.groc.model;

import java.lang.Object;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class StoreModel {
    String Name, Address, Id, Cover;
    String Location;
    ArrayList<String> Type;
    GeoPoint location;

    public StoreModel(String name, String address, String id, String cover, GeoPoint location, ArrayList<String> type) {
        Name = name;
        Address = address;
        Id = id;
        Cover = cover;
        location = location;
        Type = type;
    }

    public StoreModel(String name, String address, String cover, ArrayList<String> type, GeoPoint location) {
        Name = name;
        Address = address;
        Cover = cover;
        Type = type;
        location = location;
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

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        location = location;
    }

    public ArrayList<String> getType() {
        return Type;
    }

    public void setType(ArrayList<String> type) {
        Type = type;
    }
}
