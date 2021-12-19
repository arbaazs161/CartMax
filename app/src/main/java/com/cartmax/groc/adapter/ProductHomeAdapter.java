package com.cartmax.groc.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cartmax.groc.Products;
import com.cartmax.groc.R;
import com.cartmax.groc.model.ProductModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductHomeAdapter extends RecyclerView.Adapter<ProductHomeAdapter.productViewHolder>{

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseFirestore db;

    ArrayList<ProductModel> products;
    String userID, cartId;
    Context ctx;

    public ProductHomeAdapter(ArrayList<ProductModel> products, Context context){
        this.products = products;
        this.ctx = context;
        sharedPreferences = ctx.getSharedPreferences("MySharedPref",ctx.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("userID", "");
        db = FirebaseFirestore.getInstance();
    }



    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        productViewHolder pv;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homesingleproduct, parent, false);
        pv = new productViewHolder(view);
        return pv;
    }

    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, int position) {
        String name = products.get(position).getName();
        String category = products.get(position).getCategory();
        String imgUrl = products.get(position).getimage();
        double price = products.get(position).getPrice();
        String id = products.get(position).getId();

        holder.tvName.setText(name);
        holder.tvCategory.setText(category);
        holder.tvPrice.setText(String.valueOf(price));
        Glide.with(holder.ivProduct.getContext())
                .load(imgUrl)
                .error(R.drawable.illustration_home)
                .into(holder.ivProduct);

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.viewQuant.setVisibility(View.VISIBLE);
                holder.btnAdd.setVisibility(View.GONE);

                holder.etQuant.setText("1");
                insertFirst(id, 1, userID, price);
            }
        });

        holder.btnIncre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant = Integer.parseInt(holder.etQuant.getText().toString());

                if(quant >= 9){
                    Toast.makeText(ctx, "Limit Reached", Toast.LENGTH_SHORT).show();
                }
                else{
                    double updatedPrice = price * (quant + 1);
                    updateCart(cartId, quant+1, updatedPrice);
                }
            }
        });

        holder.btnDecre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant = Integer.parseInt(holder.etQuant.getText().toString());

                if(quant <= 1){
                    //Toast.makeText(ctx, "Limit Reached", Toast.LENGTH_SHORT).show();
                    holder.viewQuant.setVisibility(View.GONE);
                    holder.btnAdd.setVisibility(View.VISIBLE);
                    //deleteCart(String cartId);
                }
                else{
                    double updatedPrice = price * (quant - 1);
                    updateCart(cartId, quant - 1, updatedPrice);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class productViewHolder extends RecyclerView.ViewHolder{

        Button btnIncre, btnDecre, btnAdd;
        EditText etQuant;
        TextView tvName, tvPrice, tvCategory;
        LinearLayout viewQuant;
        ImageView ivProduct;

        public productViewHolder(@NonNull View itemView) {
            super(itemView);

            btnIncre = itemView.findViewById(R.id.btnIncQuant);
            btnDecre = itemView.findViewById(R.id.btnDecQuant);
            btnAdd = itemView.findViewById(R.id.addProduct);
            viewQuant = itemView.findViewById(R.id.viewQuant);

            etQuant = itemView.findViewById(R.id.etQuantityProduct);

            ivProduct = itemView.findViewById(R.id.productImageHome);

            tvName = itemView.findViewById(R.id.productNameHome);
            tvCategory = itemView.findViewById(R.id.productCategoryHome);
            tvPrice = itemView.findViewById(R.id.productPriceHome);
        }
    }

    public void insertFirst(String Id, int quant, String userId, double price){

        //db.collection("cart").add()
    }

    public void updateCart(String cartId, int quant, double price){
        //ToDo
    }

    public void deleteCart(String cartId){
        //Todo
    }
}
