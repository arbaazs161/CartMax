package com.cartmax.groc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cartmax.groc.R;
import com.cartmax.groc.viewholder.ViewHolderItemHome;
import com.cartmax.groc.viewholder.ViewHolderTopHome;

import java.util.List;

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
                View v1 = inflater.inflate(R.layout.homesinglestore, parent, false);
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

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    //Will Update Later
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) == null) {
            return 0;
        } else if (items.get(position) instanceof String) {
            return 1;
        }
        return -1;
    }
}
