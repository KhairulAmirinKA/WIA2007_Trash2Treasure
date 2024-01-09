package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class Report extends Fragment {

    private RecyclerView recyclerView;
    private ReportAdapter reportAdapter;
    private RadioGroup radioGroup;

    DataManager dataManager = DataManager.getInstance();

    public Report() {
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
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_report_item);
        reportAdapter = new ReportAdapter(generateReportItems());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(reportAdapter);

        radioGroup = view.findViewById(R.id.RGReportTracker);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.RBReportAll) {
                filterReportList(Filter.ALL);
            } else if (checkedId == R.id.RBReporPending) {
                filterReportList(Filter.PENDING);
            } else if (checkedId == R.id.RBReportCompleted) {
                filterReportList(Filter.COMPLETED);
            }
        });

        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

        //create new report
        Button BtnNewReport = view.findViewById(R.id.BtnNewReport);
        BtnNewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.DestReportMake);
            }
        });
        return view;
    }

    private List<ReportItem> generateReportItems() {
        List<ReportItem> reportItemList = new ArrayList<>();

        for (ReportItem item : dataManager.reportItems) {
            reportItemList.add(item);
        }

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

