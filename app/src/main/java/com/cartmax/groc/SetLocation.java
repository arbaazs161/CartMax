package com.cartmax.groc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cartmax.groc.model.AddressModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetLocation extends AppCompatActivity {

    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    String userID;
    long pincode;
    String Line, city;

    EditText etLine, etCity, etPincode;
    Button btnSet;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        db = FirebaseFirestore.getInstance();

        etLine = findViewById(R.id.addressLine1);
        etCity = findViewById(R.id.addressCity);
        etPincode = findViewById(R.id.addressPincode);

        userID = sharedPreferences.getString("userID", "");
        pincode = sharedPreferences.getLong("userPinCode", 0);

        if(pincode > 0){
            configureView();
        }

        btnSet = findViewById(R.id.btnSetLocation);

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Line = etLine.getText().toString();
                city = etCity.getText().toString();
                pincode = Long.parseLong(etPincode.getText().toString());
                setLocation(Line, city, pincode);
            }
        });
    }

    public void setLocation(String line, String city, long pincode){
        AddressModel addressModel = new AddressModel(city, line, userID, pincode);
        db.collection("address").add(addressModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                editor.putLong("userPinCode", pincode);
                editor.apply();
                editor.commit();

                Intent i = new Intent(SetLocation.this, Home.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void configureView(){

    }
}