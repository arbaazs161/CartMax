package com.cartmax.groc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cartmax.groc.model.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditItem extends AppCompatActivity {

    FirebaseFirestore db;
    EditText etName, etPrice, etStock;
    Button btnUpdate, btnDelete;
    TextView tvCategory;
    ImageView iv;
    String id;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        db = FirebaseFirestore.getInstance();

        etName = findViewById(R.id.editItemName);
        etPrice = findViewById(R.id.editItemPrice);
        etStock = findViewById(R.id.editItemStock);
        tvCategory = findViewById(R.id.editItemCategory);
        iv = findViewById(R.id.editItemImage);
        id = getIntent().getStringExtra("productId");

        btnUpdate = findViewById(R.id.editItemBtn);
        btnDelete = findViewById(R.id.deleteItemBtn);
        builder = new AlertDialog.Builder(this);

        loadItems(id);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                double price = Double.parseDouble(etPrice.getText().toString());
                int stock = Integer.parseInt(etStock.getText().toString());
                String category = tvCategory.getText().toString();

                updateItems(name, price, category, stock);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });
    }

    public void updateItems(String name, Double price, String category, int stock){
        ProductModel pm = new ProductModel();
        pm.setName(name);
        pm.setPrice(price);
        pm.setStock(stock);
        db.collection("Product").document(id).set(pm).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent i = new Intent(EditItem.this, HomeStore.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void deleteItem(){

        builder.setMessage("Do you want to delete this item?")
                .setIcon(R.drawable.logosmall)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collection("Product").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
        });

        AlertDialog dialog = builder.create();
        dialog.setTitle("CARTMAX");
        dialog.show();
    }

    public void loadItems(String id){

        db.collection("Product").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot != null){
                    String name = documentSnapshot.get("name").toString();
                    String price = documentSnapshot.get("price").toString();
                    String stock = "50";
                    String category = documentSnapshot.get("category").toString();
                    String imgUrl = documentSnapshot.get("image").toString();

                    Glide.with(EditItem.this)
                            .load(imgUrl)
                            .into(iv);

                    etName.setText(name);
                    etPrice.setText(price);
                    etStock.setText(stock);
                    tvCategory.setText(category);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}