package com.cartmax.groc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cartmax.groc.adapter.CartAdapter;
import com.cartmax.groc.model.CartModel;
import com.cartmax.groc.model.ProductModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    Button btnPaymentMode, btnPlaceOrder;
    String userId;
    RecyclerView rv;
    List<CartModel> cartList;
    List<ProductModel> products;

    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    FirebaseFirestore db;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        db = FirebaseFirestore.getInstance();

        btnPaymentMode = findViewById(R.id.selectMode);
        btnPlaceOrder = findViewById(R.id.placeOrder);
        cartList = new ArrayList<CartModel>();
        products = new ArrayList<ProductModel>();
        rv = findViewById(R.id.rvCart);

        String txtToPut = sharedPreferences.getString("paymentMode", "");
        userId = sharedPreferences.getString("userId", "");

        String textbtn = btnPaymentMode.getText().toString();

        if(txtToPut.isEmpty()){

        }else{
            btnPaymentMode.setText(txtToPut);
        }

        products.clear();

        adapter = new CartAdapter(products, Cart.this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        db.collection("Cart").whereEqualTo("userId", userId)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                if(!queryDocumentSnapshots.isEmpty()){
                    for(DocumentSnapshot document : documents){
                        CartModel pm = document.toObject(CartModel.class);
                        String id = document.getId();
                        Log.d("Document ID", id);
                        pm.setId(id);
                        cartList.add(pm);
                        bindProducts(pm);
                    }

                    //Adapter Updation Here
                    //Log.d("ArrayList Items", products.toString());
                    //adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(Cart.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }
        });



        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textbtn.equals("Payment Mode")){
                    Toast.makeText(Cart.this, "Please select a payment mode", Toast.LENGTH_SHORT).show();
                }
                else if(textbtn.equals("Offline")){
                    placeOrder(userId, cartList);
                }
            }
        });

        btnPaymentMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void bindProducts(CartModel cm){
        String productId = cm.getProductId();
        double price = cm.getPrice();
        int quant = cm.getQuant();

        db.collection("Product").document().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                if(documentSnapshot != null){
                    ProductModel pm = documentSnapshot.toObject(ProductModel.class);
                    pm.setStock(quant);
                    pm.setPrice(price);
                    products.add(pm);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void placeOrder(String userId, List<CartModel> myList){
        CartModel cm;

        for(int i = 0; i < myList.size(); i++){
            cm = myList.get(i);

            db.collection("Orders").add(cm).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                }
            });

            db.collection("Cart").document(cm.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                }
            });
        }

        Toast.makeText(Cart.this, "Order Placed", Toast.LENGTH_SHORT).show();


    }
}