package com.techwizards.wia2007_trash2treasure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import net.steamcrafted.materialiconlib.MaterialIconView;

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

        //back button
        MaterialIconView btnDismiss= view.findViewById(R.id.BtnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ask user's confirmation to exit without submitting report
                showConfirmationDialog(view);
            }
        });

        //map
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Maps maps = new Maps();
        fragmentTransaction.replace(R.id.FCVComplaintLocation, maps);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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

    //show confirmation dialog when user wants to back
    private void showConfirmationDialog(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext() );

        builder.setTitle("Discard Report");

        builder.setMessage("Are you sure you want to discard report? Any unsubmitted report will be lost.");

        //yes btn
        String positiveText= "Yes";
        SpannableString spannableString = new SpannableString(positiveText);

        //yes btn to become red
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, positiveText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setPositiveButton(spannableString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //go back to report main page
                Navigation.findNavController(view).popBackStack();
            }
        });

        //no btn
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



}