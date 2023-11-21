package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Report#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Report extends Fragment {

    private RecyclerView recyclerView;
    private ReportAdapter reportAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Report() {
        // Required empty public constructor
    }

    public static Report newInstance(String param1, String param2) {
        Report fragment = new Report();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_report_item);
        reportAdapter = new ReportAdapter(generateReportItems());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(reportAdapter);

        return view;
    }

    private List<ReportItem> generateReportItems() {
        List<ReportItem> reportItemList = new ArrayList<>();

        reportItemList.add(new ReportItem(
                "Improper Disposal at Public Park",
                "Littering",
                "Taman Negara Park, Kuala Lumpur",
                "John Doe",
                "Pending",
                "2023-11-19",
                "10:45 AM"
        ));

        reportItemList.add(new ReportItem(
                "Illegal Dumping in Residential Area",
                "Illegal Dumping",
                "Jalan Residential, Penang",
                "Jane Smith",
                "In Progress",
                "2023-11-19 02:15 PM",
                "02:15 PM"
        ));

        reportItemList.add(new ReportItem(
                "Overflowing Bin at Food Court",
                "Overflowing Bin",
                "Petaling Jaya Food Court, Selangor",
                "Ahmad Ibrahim",
                "Completed",
                "2023-11-19 03:30 PM",
                "03:30 PM"
        ));

        return reportItemList;
    }
}

