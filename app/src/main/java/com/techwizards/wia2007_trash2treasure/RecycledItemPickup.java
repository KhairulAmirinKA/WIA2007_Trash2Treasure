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

public class RecycledItemPickup extends Fragment {

    RecyclerView recyclerView;
    RecycledItemPickupAdapter adapter;

    public RecycledItemPickup() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycled_item_pickup, container, false);

        MaterialIconView btnDismiss = view.findViewById(R.id.BtnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        recyclerView = view.findViewById(R.id.RVRIPSchedule);
        adapter = new RecycledItemPickupAdapter(getScheduleList());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<RecyIPItem> getScheduleList() {
        List<RecyIPItem> items = new ArrayList<>();

        List<String> types = new ArrayList<>();
        types.add("Plastic and Metal");
        types.add("Paper");

        items.add(new RecyIPItem("17 January", types));
        items.add(new RecyIPItem("20 January", types));
        items.add(new RecyIPItem("24 January", types));

        return items;
    }
}