package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.views.overlay.Marker;

public class Maps extends Fragment {

    private MapView mapView;
    private Marker userMarker;

    public Maps() {
        // Required empty public constructor
    }
    public static Maps newInstance(String param1, String param2) {
        Maps fragment = new Maps();
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

        mapView = (MapView) view.findViewById(R.id.MVTruckTracker);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.getController().setZoom(12.0);

        return view;
    }

    public void updateMapLocation(double latitude, double longitude) {
        if (mapView != null) {
            mapView.getController().setCenter(new GeoPoint(latitude, longitude));
            mapView.getController().setZoom(17.0);

            if (userMarker != null) {
                mapView.getOverlays().remove(userMarker);
            }

            userMarker = new Marker(mapView);
            userMarker.setPosition(new GeoPoint(latitude, longitude));
            userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
            mapView.getOverlays().add(userMarker);

            mapView.invalidate();
        }
    }
}