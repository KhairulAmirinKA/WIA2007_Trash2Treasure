package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class RegisterDetail extends Fragment {

    public RegisterDetail() {
        // Required empty public constructor
    }

    public static RegisterDetail newInstance() {
        RegisterDetail fragment = new RegisterDetail();
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
        View view = inflater.inflate(R.layout.fragment_register_detail, container, false);

        LinearLayout registerDetails = view.findViewById(R.id.LLRegisterDetails);
        LinearLayout registerOTP = view.findViewById(R.id.LLRegisterOTP);
        LinearLayout registerSuccess = view.findViewById(R.id.LLRegisterSuccess);
        Button getOTP = view.findViewById(R.id.BtnRegisterGetOTP);
        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDetails.setVisibility(View.INVISIBLE);
                registerOTP.setVisibility(View.VISIBLE);
            }
        });

        Button verifyOTP = view.findViewById(R.id.BtnRegisterDetailOTPVerify);
        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerOTP.setVisibility(View.INVISIBLE);
                registerSuccess.setVisibility(View.VISIBLE);
            }
        });

        Button login = view.findViewById(R.id.BtnRegisterDetailReLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }
}