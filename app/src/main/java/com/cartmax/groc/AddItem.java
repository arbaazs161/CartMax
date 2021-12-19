package com.cartmax.groc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cartmax.groc.model.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity {

    FirebaseFirestore db;

    EditText etItemName, etItemPrice;
    Spinner spinnerItemCategory;
    List<String> categoryString;
    Button btnAddItem;
    String ID;
    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        db = FirebaseFirestore.getInstance();
        ID = sharedPreferences.getString("storeID", "");

        etItemName = findViewById(R.id.createItemName);
        btnAddItem = findViewById(R.id.createItemBtn);
        etItemPrice = findViewById(R.id.createItemPrice);
        spinnerItemCategory = findViewById(R.id.createItemCategory);
        categoryString = new ArrayList<>();
        categoryString.add("-- Choose Category --");
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryString){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
        };

        spinnerItemCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    //Toast.makeText(AddItem.this, "Please Choose a Category", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });


        db.collection("stores").document(ID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot != null){
                    categoryString = (List<String>) documentSnapshot.get("type");
                    Log.d("Spinner Data", categoryString.get(0));
                    //spinnerAdapter.clear();
                    spinnerAdapter.addAll(categoryString);
                    spinnerAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddItem.this, "SpinnerFailedLOL", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemCategory.setAdapter(spinnerAdapter);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = etItemName.getText().toString();
                double itemPrice = Double.parseDouble(etItemPrice.getText().toString());
                String itemCategory = spinnerItemCategory.getSelectedItem().toString();
                String StoreId = ID;

                ProductModel pm = new ProductModel(itemName, itemCategory, StoreId, itemPrice, 100,
                        "https://firebasestorage.googleapis.com/v0/b/cartmax-666ad.appspot.com/o/StoreCover%2Fillustration_home.png?alt=media&token=54a00e85-48ef-4d41-9c50-27db3a6e6439");

                db.collection("Product").add(pm).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddItem.this, "Item Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddItem.this, "Some Problem Occurred! Try Later", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}