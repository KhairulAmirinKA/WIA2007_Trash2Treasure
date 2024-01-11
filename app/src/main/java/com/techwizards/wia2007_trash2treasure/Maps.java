package com.techwizards.wia2007_trash2treasure;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Maps extends Fragment {

    private MapView mapView;
    private Marker userMarker;
    private List<Marker> recyclingMarkers = new ArrayList<>();

    public Maps() {
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
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) view.findViewById(R.id.MVTruckTracker);
        initializeMapView();

        return view;
    }

    private void initializeMapView() {
        if (mapView != null) {
            Configuration.getInstance().setUserAgentValue("com.techwizards.wia2007_trash2treasure");

            mapView.setTileSource(TileSourceFactory.MAPNIK);
            mapView.getController().setCenter(new GeoPoint(3.105506, 101.6617209));
            mapView.getController().setZoom(12.0);
        }
    }

    public void updateMapLocation(double latitude, double longitude) {
        if (mapView != null) {
            System.out.println("Location ->" + latitude + " : " + longitude);
            mapView.getController().setCenter(new GeoPoint(latitude, longitude));
            mapView.getController().setZoom(12.0);

            if (userMarker != null) {
                mapView.getOverlays().remove(userMarker);
            }
            for (Marker recyclingMarker : recyclingMarkers) {
                mapView.getOverlays().remove(recyclingMarker);
            }

            userMarker = new Marker(mapView);
            userMarker.setPosition(new GeoPoint(latitude, longitude));
            userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
            mapView.getOverlays().add(userMarker);

            performRecyclingPointsSearch(latitude, longitude);
//            System.out.println("Number of Overlays: " + mapView.getOverlays().size());

            for (Marker marker : recyclingMarkers) {
                mapView.getOverlays().add(marker);
            }

            mapView.invalidate();
        }
    }

    private void performRecyclingPointsSearch(double latitude, double longitude) {
        String searchQuery = "recycling";

        String osmApiURL = "https://nominatim.openstreetmap.org/search?" +
                           "&q=" + searchQuery +
                           "&lat=" + latitude +
                           "&lon=" + longitude +
                           "&countrycodes=my&format=json";

        PlaceSearchTask placeSearchTask = new PlaceSearchTask(this);
        placeSearchTask.execute(osmApiURL);
    }

    private void addRecyclingPointMarker(double latitude, double longitude) {
        Marker recyclingMarker = new Marker(mapView);
        recyclingMarker.setPosition(new GeoPoint(latitude, longitude));

        Drawable icon = getResources().getDrawable(R.drawable.recycle_icon);
        Bitmap iconBit = ((BitmapDrawable) icon).getBitmap();
        int width = iconBit.getWidth() / 10;
        int height = iconBit.getHeight() / 10;
        recyclingMarker.setIcon(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(iconBit, width, height, true)));

        recyclingMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        recyclingMarkers.add(recyclingMarker);

        mapView.getOverlays().add(recyclingMarker);

//        System.out.println("Recycling Point Marker Added: " + latitude + ", " + longitude);
    }

    private static class PlaceSearchTask extends AsyncTask<String, Void, List<GeoPoint>> {
        private final WeakReference<Maps> mapsWeakReference;

        PlaceSearchTask(Maps maps) {
            mapsWeakReference = new WeakReference<>(maps);
        }
        @Override
        protected List<GeoPoint> doInBackground(String... urls) {
            List<GeoPoint> result = new ArrayList<>();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(urls[0]).build();

            try {
                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();

                result = parseNominatimResponse(responseBody);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<GeoPoint> result) {
            Maps maps = mapsWeakReference.get();
            for (GeoPoint geoPoint : result) {
                maps.addRecyclingPointMarker(geoPoint.getLatitude(), geoPoint.getLongitude());
            }
        }

        private List<GeoPoint> parseNominatimResponse(String responseBody) {
            List<GeoPoint> geoPoints = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(responseBody);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    double lat = jsonObject.getDouble("lat");
                    double lon = jsonObject.getDouble("lon");
                    GeoPoint geoPoint = new GeoPoint(lat, lon);
                    geoPoints.add(geoPoint);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            for (GeoPoint geoPoint : geoPoints) {
//                System.out.println("GeoPoint: " + geoPoint.getLatitude() + ", " + geoPoint.getLongitude());
//            }
//            System.out.println("Nominatim API Response: " + responseBody);

            return geoPoints;
        }
    }
}