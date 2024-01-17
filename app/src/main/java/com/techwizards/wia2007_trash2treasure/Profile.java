package com.techwizards.wia2007_trash2treasure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.steamcrafted.materialiconlib.MaterialIconView;

public class Profile extends Fragment {

    DataManager dataManager = DataManager.getInstance();
    private ProfileAdapter profileAdapter;
    private ProfileItem userProfile;
    public Profile() {}

    public static Profile newInstance() {
        return new Profile();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileAdapter = new ProfileAdapter(view);
        userProfile = dataManager.currentUser.getCurrentUser();
        profileAdapter.populateViews(userProfile);

        //click reward icon besides the Profile TextView
        MaterialIconView reward = view.findViewById(R.id.BtnProfileReward);
        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.DestAward);
            }
        });

        //click the logout
        Button logOut = view.findViewById(R.id.BtnProfileLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLogState();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //edit profile
        Button BtnProfileEdit= view.findViewById(R.id.BtnProfileEdit);
        BtnProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //it will go to edit profile fragment
                Navigation.findNavController(view).navigate(R.id.DestEditProfile);
            }
        });

        return view;
    } //oncreate view

    private void changeLogState() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();
        dataManager.currentUser.clearCurrentUser();
        dataManager.save(getContext());
    }
}

