package com.techwizards.wia2007_trash2treasure;

import android.net.Uri;
import android.os.Bundle;

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
    private static final int REQUEST_CODE_PICK_PHOTO = 101;
    private Uri selectedPhotoUri;
    StorageReference imgRef;
    String fileName;
    String imgPath="NA";
    DataManager dataManager = DataManager.getInstance();
    public AdvertiseProduct() {
        // Required empty public constructor
    }

    public static RegisterDetail newInstance() {
        RegisterDetail fragment = new RegisterDetail();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advertise_product, container, false);

        Bundle bundle = getArguments();

        LinearLayout LLProductName = view.findViewById(R.id.LLProductName);
        LinearLayout LLPrice = view.findViewById(R.id.LLPrice);
        LinearLayout LLProductDetails = view.findViewById(R.id.LLProductDetails);
        LinearLayout LLQuantity = view.findViewById(R.id.LLQuantity);
        LinearLayout LLDescription = view.findViewById(R.id.LLDescription);
        LinearLayout LLEmail = view.findViewById(R.id.LLEmail);
        LinearLayout LLImagePicker = view.findViewById(R.id.LLImagePicker);

        // Initialize EditTexts
        EditText ETProductName = view.findViewById(R.id.ETProductName);
        EditText ETProductPrice = view.findViewById(R.id.ETProductPrice);
        EditText ETProductDetails = view.findViewById(R.id.ETProductDetails);
        EditText ETProductQuantity = view.findViewById(R.id.ETProductQuantity);
        EditText ETProductDescription = view.findViewById(R.id.ETProductDescription);
        EditText ETEmail = view.findViewById(R.id.ETEmail);

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
                String email = ETEmail.getText().toString();

                if (!TextUtils.isEmpty(productName) && !TextUtils.isEmpty(productPrice) && !TextUtils.isEmpty(productDetails) && !TextUtils.isEmpty(productQuantity) && !TextUtils.isEmpty(productDescription) && !TextUtils.isEmpty(email)) {
                    //extract the String from editText
                    String price = productPrice.toString();
                    String quantity = productQuantity.toString();



                    //upload photo to firebase storage
                    if (selectedPhotoUri!= null){
                        uploadToFirebaseStorage();
                    }

                    Product newProduct = new Product( productName, price, productDetails, quantity, productDescription, email);
                    //add to the firebase
                    dataManager.addNewProduct(newProduct);

                    Toast.makeText(getContext(), "New product is successfully submitted", Toast.LENGTH_SHORT).show();

                    //once upload successful go to marketplace page
                    Navigation.findNavController(view).popBackStack();

                }

                else {
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
                uploadPhoto(view);
            }
        });
        return view;
    }

    private void uploadPhoto(View view){
        ImagePicker.Companion.with(this)
                .crop()
                .start(REQUEST_CODE_PICK_PHOTO);
    }

    private void uploadToFirebaseStorage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

        //get current time
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_HHmm", Locale.getDefault());
        String timestamp = dateFormat.format(calendar.getTime());

        //bina nama file
        fileName = dataManager.currentUser.getCurrentUser() + "_" + timestamp;

        imgRef = storageRef.child("productPhotos/" + fileName);

        imgRef.putFile(selectedPhotoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageRef.child("productPhotos/" + fileName).getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imgPath = uri.toString();
                                    }
                                });

                        if (isAdded()) { //checks whether fragment is attached to the activity
                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        if (isAdded()) {
                            Toast.makeText(getContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                            Log.e("GAGAL", e.getMessage());
                        }
                    }
                });
    }
}