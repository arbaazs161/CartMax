package com.cartmax.groc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.cartmax.groc.model.StoreModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import com.google.android.gms.maps.model.LatLng;

public class RegisterStore extends AppCompatActivity implements OnMapReadyCallback {

    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    LatLng latLng;

    GoogleMap gMap;

    MultiAutoCompleteTextView etType;
    EditText etName, etAddress, etContact, etPinCode;
    String referenceUrl;
    String ID;
    boolean isSuccessRegistered;

    ArrayList<String> typesArray;
    String Name, Address, Contact;

    Button btnSelectImage, btnRegisterStore;

    FirebaseFirestore db;
    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    private double longitude;
    private double latitude;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_store);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapRegisterStore);
        mapFragment.getMapAsync(this);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, TYPES);

        etType = findViewById(R.id.etRegisterStoreType);
        etName = findViewById(R.id.etRegisterStoreName);
        etContact = findViewById(R.id.etRegisterStoreContact);
        etAddress = findViewById(R.id.etRegisterStoreAddress);
        etPinCode = findViewById(R.id.etRegisterStorePincode);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnRegisterStore = findViewById(R.id.registerBtnStore);
        typesArray = new ArrayList<>();
        etType.setAdapter(adapter);
        etType.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

        btnRegisterStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> typesList = splitString(etType.getText().toString());
                typesArray.addAll(typesList);
                Name = etName.getText().toString();
                Address = etAddress.getText().toString();
                Contact = etContact.getText().toString();
                Long PinCode = Long.parseLong(etPinCode.getText().toString());

                boolean res = registerStore(Name, typesArray, Address, Contact, PinCode);
                if(res) Log.d("Succeess Registering", "Hurray");
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Toast.makeText(RegisterStore.this,"Latitude : "+latitude + "Longitude "+longitude, Toast.LENGTH_LONG).show();
                            moveMap();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            Toast.makeText(RegisterStore.this,"Latitude : "+latitude + "Longitude "+longitude, Toast.LENGTH_LONG).show();
            moveMap();
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    private boolean registerStore(String name, ArrayList<String> type, String Address, String Contact, long pincode){
        isSuccessRegistered = false;

        //long pincode = 390030;

        String urlRef = UploadImage();

        GeoPoint geoPoint = new GeoPoint(latitude, longitude);

        if(urlRef != null){
            StoreModel sm = new StoreModel(name, Address, urlRef, type, geoPoint, Contact, pincode);
            Toast.makeText(RegisterStore.this, "LatLng : "+geoPoint, Toast.LENGTH_SHORT).show();
            CollectionReference addStore = db.collection("stores");

            addStore.add(sm).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    ID = documentReference.getId();
                    isSuccessRegistered = true;
                    editor.putString("storeID", ID);
                    editor.apply();
                    editor.commit();
                    Intent i = new Intent(RegisterStore.this, HomeStore.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

        return isSuccessRegistered;
    }

    private static final String[] TYPES = new String[] {
            "Milk", "Vegetable", "Grocery", "Curd", "Butter", "Apple", "Orange", "Pulses", "Rice", "Cashew", "Nuts", "Almond"
            , "Raisins", "Fenugreek", "Chips", "Tomato", "Fruits", "Dairy", "Potato"
    };

    private List<String> splitString(String Types){
        String[] typeArray = Types.split("[,]", 0);
        List<String> types = (List<String>) Arrays.asList(typeArray);

        return types;
    }

    private void SelectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    private String UploadImage(){
        referenceUrl = "https://firebasestorage.googleapis.com/v0/b/cartmax-666ad.appspot.com/o/StoreCover%2Fillustration_home.png?alt=media&token=54a00e85-48ef-4d41-9c50-27db3a6e6439";
        if(filePath != null){

            StorageReference ref
                    = storageReference
                    .child(
                            "StoreCover/"
                                    + UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            referenceUrl = uri.toString();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            referenceUrl = uri.toString();
                        }
                    });
                }
            });
        }

        return referenceUrl;
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {


            }

            catch (Exception e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng india = new LatLng(-34, 151);
        gMap.addMarker(new MarkerOptions().position(india).title("Marker in India"));

        gMap.moveCamera(CameraUpdateFactory.newLatLng(india));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void moveMap() {

        LatLng latLng = new LatLng(latitude, longitude);
        gMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("Marker in India"));

        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        gMap.getUiSettings().setZoomControlsEnabled(true);


    }
}