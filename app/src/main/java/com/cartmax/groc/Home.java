package com.cartmax.groc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cartmax.groc.adapter.HomeAdapter;
import com.cartmax.groc.model.StoreModel;
import com.google.type.LatLng;

import java.util.ArrayList;
import java.util.Arrays;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recHome);

        ArrayList<Object> homeList = new ArrayList<>();

        ArrayList<String> imgList = new ArrayList<>();

        imgList.add("Arbaaz");
        imgList.add("Arfat");
        imgList.add("Mohit");
        imgList.add("Indira");

        homeList.add(imgList);

        for(int i = 0; i < 3; i++){
            StoreModel sm = new StoreModel("Dairy", "Vadodara", "A1234", "This is store cover "+i, "54.598693,-5.925955", new ArrayList<String>(Arrays.asList("Hello", "I am Type")));
            homeList.add(sm);
        }

        HomeAdapter adapter = new HomeAdapter(homeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}