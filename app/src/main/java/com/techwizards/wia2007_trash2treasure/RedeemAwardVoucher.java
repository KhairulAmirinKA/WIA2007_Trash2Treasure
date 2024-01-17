package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.steamcrafted.materialiconlib.MaterialIconView;


public class RedeemAwardVoucher extends Fragment {
    DataManager dataManager = DataManager.getInstance();
    public RedeemAwardVoucher() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_redeem_award_voucher, container, false);

        MaterialIconView btnBack = view.findViewById(R.id.BtnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the award page
                Navigation.findNavController(view).popBackStack();
            }
        });

        TextView TVAvailablePoints =view.findViewById(R.id.TVAvailablePoints);
        String pointText = dataManager.currentUser.getCurrentUser().getPoints() + " ⭐";
        TVAvailablePoints.setText(pointText);

        return view;
    }
}