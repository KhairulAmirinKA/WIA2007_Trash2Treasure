package com.techwizards.wia2007_trash2treasure;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Home extends Fragment {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView locationTextView;

    private Map mapFragment;

    public Home() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mapFragment = new Map();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.MapHomeTruckTracker, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        locationTextView = view.findViewById(R.id.TVHomeLocation);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
        } else {
            getLocation();
        }

        return view;
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    mapFragment.updateMapLocation(latitude, longitude);
                    Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                        if (addresses != null && addresses.size() > 0) {
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();

                            String locationText = state + ", " + country;
                            locationTextView.setText(locationText);
                        } else {
                            locationTextView.setText("Malaysia");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        locationTextView.setText("Location unavailable");
                    }
                } else {
                    locationTextView.setText("Location not available");
                }
            });
        }
    }
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                1
        );
    }
}