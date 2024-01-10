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

        recyclerView = view.findViewById(R.id.RVMarketplace);
        adapter = new MarketplaceAdapter(getProducts());

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public List<MarketItem> getProducts() {
        List<MarketItem> list = new ArrayList<>();

        list.add(new MarketItem("https://i.pinimg.com/736x/dd/57/a2/dd57a2f0938eb9d2c0cdde23afd326e3.jpg", "Recycled Bag", "Good Condition", 2));
        list.add(new MarketItem("https://i.pinimg.com/564x/eb/08/40/eb0840e71c831854dc7458c2f6f76f23.jpg", "DIY Lamp", "100% Handmade", 3));
        list.add(new MarketItem("https://www.purlsoho.com/media/catalog/product/p/u/purlsoho_classicfriendshipbracelet_m-3_1.jpg?quality=85&bg-color=255,255,255&fit=bounds&height=712&width=544", "Bracelet", "100% Handmade", 6));

        return list;
    }
}