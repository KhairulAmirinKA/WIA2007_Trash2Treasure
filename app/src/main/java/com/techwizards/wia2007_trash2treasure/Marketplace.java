package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.List;

public class Marketplace extends Fragment {
    RecyclerView recyclerView;
    MarketplaceAdapter adapter;

    public Marketplace() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);

        MaterialIconView btnDismiss = view.findViewById(R.id.BtnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        MaterialIconView btnAddProduct = view.findViewById(R.id.BtnAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestAdvertiseProduct);
            }
        });

        recyclerView = view.findViewById(R.id.RVMarketplace);
        adapter = new MarketplaceAdapter(getProducts());

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public List<MarketItem> getProducts() {
        return DataManager.getInstance().productItems;
    }
}