package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.config.Configuration;

public class Map extends Fragment {

    private MapView mapView;

    public Map() {
        // Required empty public constructor
    }
    public static Map newInstance(String param1, String param2) {
        Map fragment = new Map();
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
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        Configuration.getInstance().setUserAgentValue("com.techwizards.wia2007_trash2treasure");

        mapView = view.findViewById(R.id.MVTruckTracker);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.getController().setZoom(12.0);

        return view;
    }

    public void updateMapLocation(double latitude, double longitude) {
        if (mapView != null) {
            mapView.getController().setCenter(new GeoPoint(latitude, longitude));
            mapView.getController().setZoom(17.0);
        }
    }
}