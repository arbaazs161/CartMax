package com.cartmax.groc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

        ActionBar actionBar = getSupportActionBar();

        actionBar.setIcon(R.drawable.logosmall);

        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recHome);

        ArrayList<Object> homeList = new ArrayList<>();

        ArrayList<String> imgList = new ArrayList<>();

        imgList.add("firsthome");
        imgList.add("secondhome");
        imgList.add("thirdhome");
        imgList.add("fourthhome");

        homeList.add(imgList);

        for(int i = 0; i < 10; i++){
            StoreModel sm = new StoreModel("Dairy", "Vadodara", "A1234", "This is store cover "+i, "54.598693,-5.925955", new ArrayList<String>(Arrays.asList("Hello", "I am Type")));
            homeList.add(sm);
        }

        HomeAdapter adapter = new HomeAdapter(homeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.refresh:
                Toast.makeText(this, "Refresh Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.copy:
                Toast.makeText(this, "Copy Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}