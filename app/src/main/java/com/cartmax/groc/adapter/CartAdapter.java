package com.cartmax.groc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cartmax.groc.Cart;
import com.cartmax.groc.R;
import com.cartmax.groc.model.CartModel;
import com.cartmax.groc.model.ProductModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    List<ProductModel> products;
    Context ctx;

    public CartAdapter(List<ProductModel> products, Context context){
        this.products = products;
        this.ctx = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartViewHolder pv;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homesingleproduct, parent, false);
        pv = new CartViewHolder(view);
        return pv;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        String name = products.get(position).getName();
        String category = products.get(position).getCategory();
        String imgUrl = products.get(position).getimage();
        double price = products.get(position).getPrice();
        String id = products.get(position).getId();
        holder.btnAdd.setVisibility(View.GONE);
        holder.viewQuant.setVisibility(View.VISIBLE);
        holder.btnIncre.setVisibility(View.GONE);
        holder.btnDecre.setVisibility(View.GONE);

        holder.tvName.setText(name);
        holder.tvCategory.setText(category);
        holder.tvPrice.setText(String.valueOf(price));
        holder.etQuant.setText(String.valueOf(products.get(position).getStock()));
        Glide.with(holder.ivProduct.getContext())
                .load(imgUrl)
                .error(R.drawable.illustration_home)
                .into(holder.ivProduct);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{

        Button btnIncre, btnDecre, btnAdd;
        EditText etQuant;
        TextView tvName, tvPrice, tvCategory;
        LinearLayout viewQuant;
        ImageView ivProduct;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            Button btnIncre, btnDecre, btnAdd;
            EditText etQuant;
            TextView tvName, tvPrice, tvCategory;
            LinearLayout viewQuant;
            ImageView ivProduct;
        }
    }
}
