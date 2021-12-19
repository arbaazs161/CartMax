package com.cartmax.groc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cartmax.groc.adapter.ProductHomeAdapter;
import com.cartmax.groc.adapter.ProductStoreAdapter;
import com.cartmax.groc.model.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Products extends AppCompatActivity {

    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    FirebaseFirestore db;

    RecyclerView recProducts;
    ArrayList<ProductModel> products;
    String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        db = FirebaseFirestore.getInstance();

        storeId = getIntent().getStringExtra("storeID");

        recProducts = findViewById(R.id.recProducts);

        products = new ArrayList<ProductModel>();
        ProductHomeAdapter adapter = new ProductHomeAdapter(products, Products.this);
        recProducts.setLayoutManager(new LinearLayoutManager(this));
        recProducts.setAdapter(adapter);

        db.collection("Product").whereEqualTo("storeID", storeId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(DocumentSnapshot document : documents){
                                ProductModel pm = document.toObject(ProductModel.class);
                                String id = document.getId();
                                Log.d("Document ID", id);
                                pm.setId(id);
                                products.add(pm);
                            }

                            //Adapter Updation Here
                            Log.d("ArrayList Items", products.toString());
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(Products.this, "No Products in this store", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
        });
    }
}