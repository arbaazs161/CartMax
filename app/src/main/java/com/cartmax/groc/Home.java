package com.cartmax.groc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cartmax.groc.adapter.HomeAdapter;
import com.cartmax.groc.model.ProductModel;
import com.cartmax.groc.model.StoreModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.network.ListNetworkRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.mgbramwell.geofire.android.GeoFire;
import uk.co.mgbramwell.geofire.android.model.QueryLocation;

public class Home extends AppCompatActivity {
    FirebaseFirestore db;
    CollectionReference ref;
    GeoFire gp;
    LinearLayout layout;
    Button btnGotoLocation;
    long location = 0, locationLowerBound, locationUpperBound;
    String userID;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        db = FirebaseFirestore.getInstance();
        ref = db.collection("stores");
        gp = new GeoFire(ref);
        layout = findViewById(R.id.layoutSetLocation);
        btnGotoLocation = findViewById(R.id.btnGoToSetLocation);

        location = sharedPreferences.getLong("userPinCode", 0);
        userID = sharedPreferences.getString("userID", "");


        ActionBar actionBar = getSupportActionBar();

        actionBar.setIcon(R.drawable.logosmall);

        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        recyclerView = findViewById(R.id.recHome);
        locationLowerBound = (location/1000) * 1000;
        locationUpperBound = locationLowerBound + 500;
        Log.d("Location Values", String.valueOf(locationUpperBound));
        Log.d("Location Values2", String.valueOf(locationLowerBound));

        ArrayList<Object> homeList = new ArrayList<>();

        ArrayList<String> imgList = new ArrayList<>();



        imgList.add("firsthome");
        imgList.add("secondhome");
        imgList.add("thirdhome");
        imgList.add("fourthhome");

        homeList.add(imgList);
        HomeAdapter adapter = new HomeAdapter(homeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        QueryLocation queryLocation = QueryLocation.fromDegrees(19.0760, 72.8777 );

        if(location > 0){

            recyclerView.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);

            db.collection("stores")
                    /*.whereGreaterThanOrEqualTo("pincode", locationLowerBound)
                    .whereLessThanOrEqualTo("pincode", locationUpperBound)*/
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    if(!queryDocumentSnapshots.isEmpty()){
                        for(DocumentSnapshot document : documents){
                            StoreModel pm = document.toObject(StoreModel.class);
                            Log.d("pmName", String.valueOf(pm.getPincode()));
                            String id = document.getId();
                            Log.d("Document ID", id);
                            pm.setId(id);
                            homeList.add(pm);
                        }
                        StoreModel pm = (StoreModel) homeList.get(1);
                        //Adapter Updation Here
                        Log.d("ArrayList Items", pm.getName());
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(Home.this, "No Stores Near You : (", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else{
            recyclerView.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            btnGotoLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Home.this, SetLocation.class));
                }
            });
        }

        //db.collection("stores").


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
            case R.id.setLocation:
                //Toast.makeText(this, "Set Location Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, SetLocation.class));
        }
        return super.onOptionsItemSelected(item);
    }
}