package com.techwizards.wia2007_trash2treasure;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdvertiseProduct extends Fragment {
    DataManager dataManager = DataManager.getInstance();

    private Uri imageUri;
    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            if (o != null) {
                imageUri = o;
            } else {
                Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
            }
        }
    });

    public AdvertiseProduct() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advertise_product, container, false);

        // Initialize EditTexts
        EditText ETProductName = view.findViewById(R.id.ETProductName);
        EditText ETProductPrice = view.findViewById(R.id.ETProductPrice);
        EditText ETProductDetails = view.findViewById(R.id.ETProductDetails);
        EditText ETProductQuantity = view.findViewById(R.id.ETProductQuantity);
        EditText ETProductDescription = view.findViewById(R.id.ETProductDescription);

        // Initialize Button
        Button BtnUpload = view.findViewById(R.id.BtnProductUpload);
        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click for upload
                String productName = ETProductName.getText().toString();
                String productPrice = ETProductPrice.getText().toString();
                String productDetails = ETProductDetails.getText().toString();
                String productQuantity = ETProductQuantity.getText().toString();
                String productDescription = ETProductDescription.getText().toString();
                String email = dataManager.currentUser.getCurrentUser().getEmail();

                if (!TextUtils.isEmpty(productName) && !TextUtils.isEmpty(productPrice) && !TextUtils.isEmpty(productDetails) && !TextUtils.isEmpty(productQuantity) && !TextUtils.isEmpty(productDescription)) {
                    Toast.makeText(getContext(), "New product is successfully submitted", Toast.LENGTH_SHORT).show();

                    if (imageUri != null) {
                        dataManager.addProductImage(imageUri, new DataManager.ImageUploadCallback() {
                            @Override
                            public void onUploadSuccess(String imageUri) {
                                MarketItem newItem = new MarketItem(imageUri, productName, Double.parseDouble(productPrice), productDetails, productDescription, Integer.parseInt(productQuantity), email);
                                dataManager.addNewProduct(newItem);
                                //once upload successful go to marketplace page
                                Navigation.findNavController(view).popBackStack();
                            }

                            @Override
                            public void unUploadFailure(Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } else {
                    //this means the report form is not complete. will not send to the database
                    Toast.makeText(getContext(), "Please fill the empty field(s).", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MaterialIconView btnDismiss = view.findViewById(R.id.BtnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        //handle image picker
        MaterialIconView btnImagePicker = view.findViewById(R.id.BtnImagePicker);
        btnImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
        return view;
    }
}