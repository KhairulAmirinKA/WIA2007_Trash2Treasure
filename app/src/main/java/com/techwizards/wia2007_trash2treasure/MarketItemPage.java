package com.techwizards.wia2007_trash2treasure;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import net.steamcrafted.materialiconlib.MaterialIconView;

public class MarketItemPage extends Fragment {
    public static final String POSITION = "POSITION";
    MaterialIconView BtnDismiss;
    ImageView IVProductImage;
    TextView TVProductName;
    TextView TVProductPrice;
    TextView TVProductDescription;
    Button BtnContactSeller;
    DataManager dataManager = DataManager.getInstance();
    public MarketItemPage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_market_item, container, false);

        //init UI
        initView(view);

        //back btn

        BtnDismiss.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());


        // Retrieve data from the arguments bundle. we have list of EducationResources. it has position
        int position=0;

        //get data from EducationAdapter
        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(POSITION)) {
            position = bundle.getInt(POSITION, 0);
            // Now you have the data in the receivedData variable
            // You can use it as needed
        }


        Marketplace marketplace = new Marketplace(); //instantiate fragment
        //this fragment ada method utk create list of EducationItems
        MarketItem currentItem= marketplace.getProducts().get(position); //current EducationItem

        //set name
        TVProductName.setText( currentItem.getName() );

        //set image from remote
        Glide.with(view).load( currentItem.getImage()).into(IVProductImage);

        //set the price
        double price= currentItem.getPrice();

        TVProductPrice.setText(String.format("RM%.2f", price)); //perpuluhan 2 digit

        //set the description
        TVProductDescription.setText(currentItem.getDescription());

        //click the like icon
        BtnContactSeller.setOnClickListener(view12 -> {
            // Assuming you want to add 10 points when a user purchases an item
            int additionalPoints = 10;

            ProfileItem currentUser = dataManager.currentUser.getCurrentUser();

            if (currentUser != null) {
                FirebaseService firebaseService = FirebaseService.getInstance();

                firebaseService.updateUserPoints(currentUser, additionalPoints, task -> {
                    if (task.isSuccessful()) {
                        // Update the local data manager
                        dataManager.currentUser.getCurrentUser().setPoints(currentUser.getPoints());

                        // Show a toast or any UI feedback for the successful purchase
                        Toast.makeText(getContext(), "Purchase successful. " + additionalPoints + " points obtained.", Toast.LENGTH_SHORT).show();

                        // Navigate back to the previous screen
                        Navigation.findNavController(view12).popBackStack();
                    } else {
                        Exception exception = task.getException();
                        if (exception != null) {
                            exception.printStackTrace();
                        }
                    }
                });
            }
        });

        return view;
    }

    //init UI
    private void initView(View view) {
        BtnDismiss= view.findViewById(R.id.BtnDismiss);

        IVProductImage= view.findViewById(R.id.IVProductImage);

        TVProductName= view.findViewById(R.id.TVProductName);

        TVProductPrice= view.findViewById(R.id.TVProductPrice);

        TVProductDescription= view.findViewById(R.id.TVProductDetails);

        BtnContactSeller= view.findViewById(R.id.BtnContactSeller);
    }
}
