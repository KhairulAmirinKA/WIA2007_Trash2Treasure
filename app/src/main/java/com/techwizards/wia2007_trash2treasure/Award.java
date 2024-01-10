package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.steamcrafted.materialiconlib.MaterialIconView;

public class Award extends Fragment {

    DataManager dataManager = DataManager.getInstance();
    public Award() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_award, container, false);

        MaterialIconView btnDismiss = view.findViewById(R.id.BtnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        TextView TVAwardPoints = view.findViewById(R.id.TVAwardUserPoints);
        String pointText = dataManager.currentUser.getCurrentUser().getPoints() + " ⭐";
        TVAwardPoints.setText(pointText);

        //complaint btn
        Button BtnAwardComplaint = view.findViewById(R.id.BtnAwardComplaint);
        BtnAwardComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.DestReport);
            }
        });

        return view;
    }
}