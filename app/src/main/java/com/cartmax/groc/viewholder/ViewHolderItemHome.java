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

    public ViewHolderItemHome(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.nameStoreHome);
        tvRating = itemView.findViewById(R.id.ratingStoreHome);
        tvType = itemView.findViewById(R.id.typeStoreHome);

        coverStore = itemView.findViewById(R.id.coverStoreHome);
    }
}
