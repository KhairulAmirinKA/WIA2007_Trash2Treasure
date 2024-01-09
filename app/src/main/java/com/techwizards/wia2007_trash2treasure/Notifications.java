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
import java.util.Date;
import java.util.List;

public class Notifications extends Fragment {

    private RecyclerView recyclerView;
    private NotificationsAdapter notificationsAdapter;

    public Notifications() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        MaterialIconView btnDismiss = view.findViewById(R.id.BtnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        recyclerView = view.findViewById(R.id.RVNotifications);
        notificationsAdapter = new NotificationsAdapter(getNotifications());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(notificationsAdapter);

        notificationsAdapter.setOnItemClickListener(position -> {
            NotificationsItem clickedItem = notificationsAdapter.getItem(position);
            clickedItem.setRead(true);
            notificationsAdapter.notifyItemChanged(position);
        });

        return view;
    }

    private List<NotificationsItem> getNotifications() {
        List<NotificationsItem> list = new ArrayList<>();

        list.add(new NotificationsItem(
                "Reminder",
                "10:09 AM",
                new Date().toString(),
                "Today's truck is expected to arrive around 14 minutes.")
        );

        return list;
    }
}