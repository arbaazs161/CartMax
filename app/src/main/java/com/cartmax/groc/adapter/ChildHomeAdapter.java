package com.cartmax.groc.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cartmax.groc.R;

import java.util.ArrayList;

public class ChildHomeAdapter extends RecyclerView.Adapter<ChildHomeAdapter.ChildViewHolder> {

    ArrayList<String> imageList;

    public ChildHomeAdapter(ArrayList<String> imageList){
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChildViewHolder viewHolder;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homechildsingleimage, parent, false);
        viewHolder = new ChildViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        String fname = imageList.get(position);

        String PackageName = holder.img.getContext().getPackageName();

        int imgId = holder.img.getResources().getIdentifier(PackageName+":drawable/"+fname , null, null);
        System.out.println("IMG ID :: "+imgId);

        holder.img.setImageBitmap(BitmapFactory.decodeResource(holder.img.getResources(), imgId));
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ChildViewHolder extends RecyclerView.ViewHolder{

        ImageView img;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageHomeChild);
        }
    }
}
