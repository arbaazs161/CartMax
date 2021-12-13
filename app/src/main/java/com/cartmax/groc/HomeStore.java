package com.cartmax.groc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import com.cartmax.groc.adapter.ProductStoreAdapter;
import com.cartmax.groc.model.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeStore extends AppCompatActivity {

    RecyclerView recView;
    ArrayList<ProductModel> products;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_store);

        db = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();

        actionBar.setIcon(R.drawable.logosmall);

        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        recView = findViewById(R.id.recHomeStore);



        products = new ArrayList<ProductModel>();
        ProductStoreAdapter adapter = new ProductStoreAdapter(products);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adapter);

        db.collection("Product")
                .whereEqualTo("storeID", "08OGJIaivFqnCgXajVnu")
                .get()
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
                }else{
                    Toast.makeText(HomeStore.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeStore.this, "Please Try again later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menustore, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        switch (item.getItemId()){
            case R.id.storeDetails:
                Toast.makeText(this, "Store Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.orderStore:
                Toast.makeText(this, "Orders Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addItem:
                Toast.makeText(this, "Add Item Clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}