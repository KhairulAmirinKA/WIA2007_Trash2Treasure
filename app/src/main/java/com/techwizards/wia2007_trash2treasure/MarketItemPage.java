package com.techwizards.wia2007_trash2treasure;

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
    public MarketItemPage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_market_item, container, false);

        //init UI
        initView(view);

        //back btn

        BtnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


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
        TVProductPrice.setText(String.valueOf(currentItem.getPrice()));

        //set the description
        TVProductDescription.setText(currentItem.getDescription());

        //click the like icon
        BtnContactSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Purchased successful. 10 points obtained.", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
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
