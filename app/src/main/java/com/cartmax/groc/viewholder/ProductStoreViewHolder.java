package com.cartmax.groc.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cartmax.groc.R;

public class ProductStoreViewHolder extends RecyclerView.ViewHolder {

    TextView tvName, tvPrice, tvStock;
    ImageView imageProduct;


    public ProductStoreViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.nameProductStore);
        tvPrice = itemView.findViewById(R.id.priceHomeStore);
        tvStock = itemView.findViewById(R.id.stockProductStore);
        imageProduct = itemView.findViewById(R.id.coverProductStore);
    }

    public ImageView getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(ImageView imageProduct) {
        this.imageProduct = imageProduct;
    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public TextView getTvPrice() {
        return tvPrice;
    }

    public void setTvPrice(TextView tvPrice) {
        this.tvPrice = tvPrice;
    }

    public TextView getTvStock() {
        return tvStock;
    }

    public void setTvStock(TextView tvStock) {
        this.tvStock = tvStock;
    }
}
