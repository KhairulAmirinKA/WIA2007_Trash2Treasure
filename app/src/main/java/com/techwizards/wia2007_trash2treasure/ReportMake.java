package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ReportMake extends Fragment {
    public ReportMake() {
        // Required empty public constructor
    }

    public static ReportMake newInstance() {
        ReportMake fragment = new ReportMake();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_report_make, container, false);

        //users can choose the local authority from drop down list
        handleLocalAuthoritySpinner(view);

        return view;
    }


    //handle the name local authority in dropdown spinner
    private void handleLocalAuthoritySpinner(View view) {
        //get spinner
        Spinner SpinnerReportLocalAuthority= view.findViewById(R.id.SpinnerReportLocalAuthority);

        String[] options= {"Majlis Bandaraya Shah Alam", "Majlis Bandaraya Petaling Jaya", "Majlis Bandaraya Kuala Selangor"};

        ArrayAdapter<String> adapter= new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item
        , options);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SpinnerReportLocalAuthority.setAdapter(adapter);
    }



}