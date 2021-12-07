package com.cartmax.groc.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cartmax.groc.R;

public class ViewHolderTopHome extends RecyclerView.ViewHolder{

    RecyclerView rcView;

    public RecyclerView getRcView() {
        return rcView;
    }

    public void setRcView(RecyclerView rcView) {
        this.rcView = rcView;
    }

    public ViewHolderTopHome(@NonNull View itemView) {
        super(itemView);

        rcView = itemView.findViewById(R.id.recyclerViewChild);
    }
}
