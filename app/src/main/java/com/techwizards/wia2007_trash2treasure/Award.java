package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        TextView TVAwardPoints = view.findViewById(R.id.TVAwardUserPoints);
        String pointText = dataManager.currentUser.getCurrentUser().getPoints() + " ⭐";
        TVAwardPoints.setText(pointText);

        return view;
    }
}