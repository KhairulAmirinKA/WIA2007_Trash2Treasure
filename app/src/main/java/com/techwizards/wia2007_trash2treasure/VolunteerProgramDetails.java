package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialIconView;


public class VolunteerProgramDetails extends Fragment {

    public static final String VOLUNTEER_ITEM_KEY = "VOLUNTEER_ITEM_KEY";

    TextView TV_VPDetailsTitle,TV_VPDetailsDesc, TV_VPVenue, TV_VPDetailsTime,TV_VPDetailsStartDate, TV_VPDetailsEndDate;
    ImageView IV_VPDetailsImg;
    Button BtnVolunteerJoin;

    public VolunteerProgramDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_volunteerprogram_details, container, false);

        //init all UI
        initView(view);

        //onclick back btn
        MaterialIconView BtnDismiss = view.findViewById(R.id.BtnDismiss);
        BtnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        //retrieve data from bundle sent from VolunteerAdapter
        VolunteerItem currentItem;

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(VOLUNTEER_ITEM_KEY)) {
            currentItem = (VolunteerItem) bundle.getSerializable(VOLUNTEER_ITEM_KEY);


        //get details
        TV_VPDetailsTitle.setText(currentItem.volunteerTitle);
        TV_VPDetailsDesc.setText(currentItem.volunteerDesc);
        //TODO: TV_VPVenue.setText(current);
        TV_VPDetailsStartDate.setText(currentItem.volunteerStartDate);
        TV_VPDetailsEndDate.setText(currentItem.volunteerEndDate);
        TV_VPDetailsTime.setText(currentItem.volunteerTime);

        Picasso.get().load(currentItem.ImagePath).into(IV_VPDetailsImg);

    } //if bundle

        //join volunteer. will go to RegistrationForm
        BtnVolunteerJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.DestVolunteerRegistration);
            }
        });






        return view;

    }

    private void initView(View view) {

        // Find TextViews
        TV_VPDetailsTitle = view.findViewById(R.id.TV_VPDetailsTitle);
        TV_VPDetailsDesc = view.findViewById(R.id.TV_VPDetailsDesc);
        TV_VPVenue = view.findViewById(R.id.TV_VPVenue);
        TV_VPDetailsTime = view.findViewById(R.id.TV_VPDetailsTime);
        TV_VPDetailsStartDate = view.findViewById(R.id.TV_VPDetailsStartDate);
        TV_VPDetailsEndDate= view.findViewById(R.id.TV_VPDetailsEndDate);

        // Find ImageView
        IV_VPDetailsImg = view.findViewById(R.id.IV_VPDetailsImg);

        // Find Button
        BtnVolunteerJoin = view.findViewById(R.id.BtnVolunteerJoin);

    }
}