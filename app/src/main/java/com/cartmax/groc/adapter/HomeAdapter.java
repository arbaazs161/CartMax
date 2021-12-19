package com.cartmax.groc.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cartmax.groc.Products;
import com.cartmax.groc.R;
import com.cartmax.groc.SetLocation;
import com.cartmax.groc.model.StoreModel;
import com.cartmax.groc.viewholder.ViewHolderItemHome;
import com.cartmax.groc.viewholder.ViewHolderTopHome;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Object> items;

    public HomeAdapter(List<Object> list){
        this.items = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case 0:
                View v1 = inflater.inflate(R.layout.childrecyclerhome, parent, false);
                viewHolder = new ViewHolderTopHome(v1);
                break;

            case 1:
                View v2 = inflater.inflate(R.layout.homesinglestore, parent, false);
                viewHolder = new ViewHolderItemHome(v2);
                break;

            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder = new ViewHolderTopHome(v);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch(holder.getItemViewType()){
            case 0:
                ViewHolderTopHome vh1 = (ViewHolderTopHome) holder;
                configureViewHolderTop(vh1, position);
                break;

            case 1:
                ViewHolderItemHome vh2 = (ViewHolderItemHome) holder;
                configureViewHolderItem(vh2, position);
                break;

            default:

                break;
        }

    }

    void configureViewHolderItem(ViewHolderItemHome vh, int pos){
        StoreModel sm = (StoreModel) items.get(pos);
        if(sm != null){
            String url = sm.getCover();
            vh.getTvName().setText(sm.getName());
            vh.getTvType().setText(sm.getType().toString());
            vh.getTvRating().setText(sm.getAddress());
            Log.d("Name", sm.getName());
            Glide.with(vh.getCoverStore().getContext())
                    .load(url)
                    .error(R.drawable.illustration_home)
                    .into(vh.getCoverStore());
            //vh.getTvName().setText(sm.getName());

            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(vh.itemView.getContext(), Products.class);
                    i.putExtra("storeID", sm.getId());
                    vh.itemView.getContext().startActivity(i);
                }
            });
        }
    }

    @SuppressLint("WrongConstant")
    void configureViewHolderTop(ViewHolderTopHome vh, int pos){
        ArrayList<String> images = (ArrayList<String>) items.get(pos);

        if(images != null){
            ChildHomeAdapter adapter = new ChildHomeAdapter(images);
            vh.getRcView().setLayoutManager(new LinearLayoutManager(vh.getRcView().getContext(), LinearLayout.HORIZONTAL, false));
            vh.getRcView().setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    //Will Update Later
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof ArrayList) {
            return 0;
        } else if (items.get(position) instanceof StoreModel) {
            return 1;
        }
        return -1;
    }
}
