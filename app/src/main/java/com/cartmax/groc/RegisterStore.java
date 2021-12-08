package com.cartmax.groc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.cartmax.groc.model.StoreModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RegisterStore extends AppCompatActivity {
    MultiAutoCompleteTextView etType;
    EditText etName, etAddress;
    String referenceUrl;
    String ID;
    boolean isSuccessRegistered;

    ArrayList<String> typesArray;
    String Name, Address;

    Button btnSelectImage, btnRegisterStore;

    FirebaseFirestore db;
    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_store);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, TYPES);

        etType = findViewById(R.id.etRegisterStoreType);
        etName = findViewById(R.id.etRegisterStoreName);
        etAddress = findViewById(R.id.etRegisterStoreAddress);
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

                boolean res = registerStore(Name, typesArray, Address);
                if(res) Log.d("Succeess Registering", "Hurray");
            }
        });
    }

    private boolean registerStore(String name, ArrayList<String> type, String Address){
        isSuccessRegistered = false;


        String urlRef = UploadImage();

        if(urlRef != null){
            StoreModel sm = new StoreModel(name, Address, urlRef, type);
            CollectionReference addStore = db.collection("stores");

            addStore.add(sm).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    ID = documentReference.getId();
                    isSuccessRegistered = true;
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
}