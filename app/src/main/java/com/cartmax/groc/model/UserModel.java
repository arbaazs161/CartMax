package com.cartmax.groc.model;

import com.google.firebase.firestore.auth.User;

public class UserModel {

    String Fname, Lname, Contact, Id;

    public UserModel(){

    }

    public UserModel(String fname, String lname, String contact, String id) {
        Fname = fname;
        Lname = lname;
        Contact = contact;
        Id = id;
    }

    public UserModel(String fname, String lname, String contact) {
        Fname = fname;
        Lname = lname;
        Contact = contact;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
