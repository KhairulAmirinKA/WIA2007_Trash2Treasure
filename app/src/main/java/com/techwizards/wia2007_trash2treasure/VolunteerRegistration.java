package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import net.steamcrafted.materialiconlib.MaterialIconView;


public class VolunteerRegistration extends Fragment {

    Button BtnVRegEnroll;
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

        //click enroll
        BtnVRegEnroll.setOnClickListener( view1->{
            Toast.makeText(getContext(), "hahahha", Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putBoolean("IS_ENROLL_KEY", true);

            Navigation.findNavController(view).navigate(R.id.DestVolunteer, bundle);
        });


        return view;
    }

    private void initView(View view) {

        BtnVRegEnroll =view.findViewById(R.id.BtnVRegEnroll);
    }
}