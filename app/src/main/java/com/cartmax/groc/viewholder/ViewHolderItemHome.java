package com.cartmax.groc.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cartmax.groc.R;

public class ViewHolderItemHome extends RecyclerView.ViewHolder{

    TextView tvName, tvRating, tvType;
    ImageView coverStore;

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public TextView getTvRating() {
        return tvRating;
    }

    public void setTvRating(TextView tvRating) {
        this.tvRating = tvRating;
    }

    public TextView getTvType() {
        return tvType;
    }

    public void setTvType(TextView tvType) {
        this.tvType = tvType;
    }

    public ImageView getCoverStore() {
        return coverStore;
    }

    public void setCoverStore(ImageView coverStore) {
        this.coverStore = coverStore;
    }

    public ViewHolderItemHome(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.nameStoreHome);
        tvRating = itemView.findViewById(R.id.ratingStoreHome);
        tvType = itemView.findViewById(R.id.typeStoreHome);

        coverStore = itemView.findViewById(R.id.coverStoreHome);
    }
}
