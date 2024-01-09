package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.osmdroid.util.GeoPoint;

public class NearbyRecyclingBin extends Fragment {
    public NearbyRecyclingBin() {}

    public static NearbyRecyclingBin newInstance() {
        NearbyRecyclingBin fragment = new NearbyRecyclingBin();
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
        View view = inflater.inflate(R.layout.fragment_nearby_recycling_bin, container, false);

        MaterialIconView btnDismiss = view.findViewById(R.id.BtnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Maps maps = new Maps();
        fragmentTransaction.replace(R.id.FCVNearbyMap, maps);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        return view;
    }
}