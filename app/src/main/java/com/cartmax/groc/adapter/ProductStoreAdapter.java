package com.cartmax.groc.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cartmax.groc.AddItem;
import com.cartmax.groc.EditItem;
import com.cartmax.groc.Home;
import com.cartmax.groc.HomeStore;
import com.cartmax.groc.R;
import com.cartmax.groc.model.ProductModel;
import com.cartmax.groc.viewholder.ProductStoreViewHolder;

import java.util.ArrayList;

public class ProductStoreAdapter extends RecyclerView.Adapter<ProductStoreViewHolder> {

    ArrayList<ProductModel> products;

    public ProductStoreAdapter(ArrayList<ProductModel> products){
        this.products = products;
    }

    @NonNull
    @Override
    public ProductStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductStoreViewHolder viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleproductstore, parent, false);
        viewHolder = new ProductStoreViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ProductStoreViewHolder holder, int position) {
        String name = products.get(position).getName();
        String price = String.valueOf((products.get(position).getPrice()));
        String stock = String.valueOf(products.get(position).getStock());
        String imageUrl = products.get(position).getimage();
        String id = products.get(position).getId();

        holder.getTvName().setText(name);
        holder.getTvPrice().setText(price);
        holder.getTvStock().setText(stock);
        Glide.with(holder.getImageProduct().getContext())
                .load(imageUrl)
                .into(holder.getImageProduct());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(holder.itemView.getContext(), EditItem.class));
                intent.putExtra("productId", id);
                //Toast.makeText(holder.itemView.getContext(), id, Toast.LENGTH_SHORT).show();
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
