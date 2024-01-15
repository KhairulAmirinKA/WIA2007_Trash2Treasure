package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.w3c.dom.Text;


public class VolunteerRegistration extends Fragment {

    public static final String VOLUNTEER_ITEM_KEY = "VOLUNTEER_ITEM_KEY";

    TextView TV_VRegVolunteerName;
    Button BtnVRegEnroll;

    VolunteerItem currentItem;

    DataManager dataManager = DataManager.getInstance();

    public VolunteerRegistration() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_volunteer_registration, container, false);

        //init UI
        initView(view);

        //back btn
        MaterialIconView BtnDismiss = view.findViewById(R.id.BtnDismiss);
        BtnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        Bundle bundle = getArguments();
        if (bundle!= null && bundle.containsKey(VOLUNTEER_ITEM_KEY)){
            currentItem = (VolunteerItem) bundle.getSerializable(VOLUNTEER_ITEM_KEY);
        }

        //set the title
        if (currentItem!= null) {
            TV_VRegVolunteerName.setText(currentItem.volunteerTitle);
        }

        Bundle bundle2 = new Bundle();
        bundle2.putSerializable(VOLUNTEER_ITEM_KEY, currentItem);

        //click enroll
        BtnVRegEnroll.setOnClickListener( view1->{

            currentItem.setEnrolled(true);
            Toast.makeText(getContext(), currentItem.volunteerTitle+currentItem.isEnrolled, Toast.LENGTH_SHORT).show();

            dataManager.addNewVolunteer(currentItem);
            Navigation.findNavController(view).navigate(R.id.DestVolunteer, bundle2);
        });


        return view;
    }

    private void initView(View view) {

        TV_VRegVolunteerName= view.findViewById(R.id.TV_VRegVolunteerName);
        BtnVRegEnroll =view.findViewById(R.id.BtnVRegEnroll);
    }
}