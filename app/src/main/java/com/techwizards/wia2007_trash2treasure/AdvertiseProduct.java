package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import net.steamcrafted.materialiconlib.MaterialIconView;

public class AdvertiseProduct extends Fragment {


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

            //once upload successful go to marketplace page
                Navigation.findNavController(view).popBackStack();
            }
        });

        MaterialIconView btnDismiss = view.findViewById(R.id.BtnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        MaterialIconView btnImagePicker = view.findViewById(R.id.BtnImagePicker);
        btnImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}