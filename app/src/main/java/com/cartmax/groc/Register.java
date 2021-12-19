package com.cartmax.groc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cartmax.groc.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    String Fname, Lname, Contact, Id;
    //SharedPreferences sharedPreferences;
    EditText etFname, etLname;
    Button btnReg;
    FirebaseFirestore fb;

    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFname = findViewById(R.id.etRegisterFName);
        etLname = findViewById(R.id.etRegisterLName);

        btnReg = findViewById(R.id.registerBtn);

        Contact = getIntent().getStringExtra("contact");
        fb = FirebaseFirestore.getInstance();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etFname.getText().toString()) && TextUtils.isEmpty(etLname.getText().toString())) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(Register.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {

                    Fname = etFname.getText().toString();
                    Lname = etLname.getText().toString();
                    registerUser(Fname, Lname, Contact);
                }
            }
        });

    }

    private void registerUser(String Fname, String Lname, String Contact){
        CollectionReference addUser = fb.collection("users");

        UserModel userModel = new UserModel(Fname, Lname, Contact);

        addUser.add(userModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Id = documentReference.getId();

                editor.putString("userID", Id);
                editor.apply();
                editor.commit();

                Intent i = new Intent(Register.this, Home.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}