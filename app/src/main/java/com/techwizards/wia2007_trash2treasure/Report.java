package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Report extends Fragment {

    private RecyclerView recyclerView;
    private ReportAdapter reportAdapter;
    private RadioGroup radioGroup;

    public Report() {
        // Required empty public constructor
    }

    public static Report newInstance() {
        Report fragment = new Report();
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
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_report_item);
        reportAdapter = new ReportAdapter(generateReportItems());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(reportAdapter);

        radioGroup = view.findViewById(R.id.RGReportTracker);
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.RBReportAll) {
                filterReportList(Filter.ALL);
            } else if (checkedId == R.id.RBReporPending) {
                filterReportList(Filter.PENDING);
            } else if (checkedId == R.id.RBReportCompleted) {
                filterReportList(Filter.COMPLETED);
            }
        });

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
                "19-11-2023",
                "10:45 AM"
        ));

        reportItemList.add(new ReportItem(
                "Illegal Dumping in Residential Area",
                "Illegal Dumping",
                "Jalan Residential, Penang",
                "Jane Smith",
                "In Progress",
                "19-11-2023",
                "02:15 PM"
        ));

        reportItemList.add(new ReportItem(
                "Overflowing Bin at Food Court",
                "Overflowing Bin",
                "Petaling Jaya Food Court, Selangor",
                "Ahmad Ibrahim",
                "Completed",
                "19-11-2023",
                "03:30 PM"
        ));

        return reportItemList;
    }

    private void filterReportList(Filter filter) {
        List<ReportItem> filteredList = new ArrayList<>();

        for (ReportItem reportItem : generateReportItems()) {
            switch (filter) {
                case ALL:
                    filteredList.add(reportItem);
                    break;
                case PENDING:
                    if (reportItem.getStatus().equals("Pending") || reportItem.getStatus().equals("In Progress")) {
                        filteredList.add(reportItem);
                    }
                    break;
                case COMPLETED:
                    if (reportItem.getStatus().equals("Completed")) {
                        filteredList.add(reportItem);
                    }
                    break;
            }
        }

        reportAdapter.updateList(filteredList);
    }

    private enum Filter {
        ALL, PENDING, COMPLETED
    }
}

