package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.List;

public class HouseholdWastePickupSchedule extends Fragment {

    RecyclerView recyclerView;
    HWPSHouseTypeAdapter adapter;

    public HouseholdWastePickupSchedule() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_household_waste_pickup_schedule, container, false);

        MaterialIconView btnDismiss = view.findViewById(R.id.BtnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        recyclerView = view.findViewById(R.id.RVHWPSCollection);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new HWPSHouseTypeAdapter(getCollection());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<HWPSItem> getCollection() {
        List<HWPSItem> items = new ArrayList<>();

        List<HWPSItem.CollectionType> collectionTypes = new ArrayList<>();
        collectionTypes.add(new HWPSItem.CollectionType("Domestic Waste", "Ecery Thursday", "27 May", "bin"));
        collectionTypes.add(new HWPSItem.CollectionType("Bulk Garbage", "Every Friday", "28 May", "eco"));

        items.add(new HWPSItem("Landed Residential House", collectionTypes));
        items.add(new HWPSItem("Multi-Storey Residential House", collectionTypes));

        return items;
    }
}