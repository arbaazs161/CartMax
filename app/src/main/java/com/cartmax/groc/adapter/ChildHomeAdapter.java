package com.cartmax.groc.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChildHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<String> imageList;

    public ChildHomeAdapter(ArrayList<String> imageList){
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ChildViewHolder extends RecyclerView.ViewHolder{

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
