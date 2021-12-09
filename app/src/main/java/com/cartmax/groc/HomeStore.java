package com.cartmax.groc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeStore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_store);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setIcon(R.drawable.logosmall);

        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menustore, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        switch (item.getItemId()){
            case R.id.storeDetails:
                Toast.makeText(this, "Store Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.orderStore:
                Toast.makeText(this, "Orders Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addItem:
                Toast.makeText(this, "Add Item Clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}