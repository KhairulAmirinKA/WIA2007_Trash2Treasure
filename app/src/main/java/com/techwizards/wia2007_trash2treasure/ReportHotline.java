package com.techwizards.wia2007_trash2treasure;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.steamcrafted.materialiconlib.MaterialIconView;

public class ReportHotline extends Fragment {


    public ReportHotline() {
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
       View view=  inflater.inflate(R.layout.fragment_report_hotline, container, false);

        //back btn
        MaterialIconView BtnDismiss= view.findViewById(R.id.BtnDismiss);
        BtnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        //call btn
        Button BtnHotlineCall = view.findViewById(R.id.BtnHotlineCall);
        BtnHotlineCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeHotlineCall(BtnHotlineCall);
            }
        });

        //redirect to Whatsapp
        TextView TVHotlineWhatsApp = view.findViewById(R.id.TVHotlineWhatsApp);
        TVHotlineWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeWhatsAppMessage(TVHotlineWhatsApp);
            }
        });

        return view;

    }

    private void makeWhatsAppMessage(TextView textView) {

        //remove non-digit
        String whatsAppNum= textView.getText().toString().replaceAll("\\D+","");

        Uri browserUri= Uri.parse("http://wa.me/6" + whatsAppNum);

        Intent browserIntent= new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(browserUri);
        startActivity(browserIntent);
    }

    private void makeHotlineCall(Button button) {

        //button.getText will return the nombor hotline tu
        Uri phoneUri= Uri.parse("tel:"+ button.getText().toString() );

        Intent intent = new Intent(Intent.ACTION_DIAL, phoneUri);

        try {
            startActivity(intent);
        }
        catch (Exception e){
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }


    }
}