package com.techwizards.wia2007_trash2treasure;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView locationTextView;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                locationTextView.setText("Location permission denied");
            }
        }
    }
}