package com.techwizards.wia2007_trash2treasure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterDetail extends Fragment {

    private final long resendTimer = 60;
    private boolean resendStatus = false;
    TextView resendTimeLeft;
    TextView resendButton;

    private String verificationID;

    ProfileItem newProfile;

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

        Bundle bundle = getArguments();

        LinearLayout registerDetails = view.findViewById(R.id.LLRegisterDetails);
        LinearLayout registerOTP = view.findViewById(R.id.LLRegisterOTP);
        LinearLayout registerSuccess = view.findViewById(R.id.LLRegisterSuccess);
        Button getOTP = view.findViewById(R.id.BtnRegisterGetOTP);
        ProgressBar getOTPPB = view.findViewById(R.id.PBRegisterGetOTP);

        EditText ETProfileName = view.findViewById(R.id.ETRegisterDetailName);
        EditText ETProfilePhone = view.findViewById(R.id.ETRegisterDetailPhone);
        EditText ETOTP1 = view.findViewById(R.id.ETRegisterDetailOTP1);
        EditText ETOTP2 = view.findViewById(R.id.ETRegisterDetailOTP2);
        EditText ETOTP3 = view.findViewById(R.id.ETRegisterDetailOTP3);
        EditText ETOTP4 = view.findViewById(R.id.ETRegisterDetailOTP4);
        EditText ETOTP5 = view.findViewById(R.id.ETRegisterDetailOTP5);
        EditText ETOTP6 = view.findViewById(R.id.ETRegisterDetailOTP6);
        resendTimeLeft = view.findViewById(R.id.TVRegisterDetailTimer);
        resendButton = view.findViewById(R.id.BtnRegisterResendOTP);

        EditText[] otpEText = {ETOTP1, ETOTP2, ETOTP3, ETOTP4, ETOTP5, ETOTP6};
        addOTPListener(otpEText);
        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOTPPB.setVisibility(View.VISIBLE);
                getOTP.setVisibility(View.INVISIBLE);

                String name = ETProfileName.getText().toString();
                String phone = ETProfilePhone.getText().toString();

                //add +60 in front of the phone. because it is Malaysian
                //for OTP verification
                String phoneFormat = phone.contains("+6") ? phone : "+6" + phone;

                bundle.putString("name", name);
                bundle.putString("phone", phoneFormat);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneFormat,
                        60,
                        TimeUnit.SECONDS,
                        getActivity(),
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                getOTPPB.setVisibility(View.GONE);
                                getOTP.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                getOTPPB.setVisibility(View.GONE);
                                getOTP.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verifID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getOTPPB.setVisibility(View.GONE);
                                getOTP.setVisibility(View.VISIBLE);

                                bundle.putString("verificationID", verifID);
                                verificationID = verifID;

                                registerDetails.setVisibility(View.GONE);
                                registerOTP.setVisibility(View.VISIBLE);
                            }
                        }
                );

                //timer
                startCountdownTimer();

                TextView TVPhone = view.findViewById(R.id.TVRegisterDetailPhone);
                String otpText = "Your OTP has been sent to " + phone;
                TVPhone.setText(otpText);
            }
        });

        //when users click the resendButton
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (resendStatus){
                    startCountdownTimer();
                }
            }
        });

        Button verifyOTP = view.findViewById(R.id.BtnRegisterDetailOTPVerify);
        ProgressBar verifyPB = view.findViewById(R.id.PBRegisterVerifyOTP);

        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = ETOTP1.getText().toString() +
                        ETOTP2.getText().toString() +
                        ETOTP3.getText().toString() +
                        ETOTP4.getText().toString() +
                        ETOTP5.getText().toString() +
                        ETOTP6.getText().toString();

                if (verificationID != null) {
                    verifyPB.setVisibility(View.VISIBLE);
                    verifyOTP.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationID,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    verifyPB.setVisibility(View.GONE);
                                    verifyOTP.setVisibility(View.VISIBLE);

                                    if (task.isSuccessful()) {
                                        registerOTP.setVisibility(View.GONE);
                                        registerSuccess.setVisibility(View.VISIBLE);
                                    } else {
                                        Toast.makeText(getActivity(), "Verification code entered was invalid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        Button login = view.findViewById(R.id.BtnRegisterDetailReLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newProfile = new ProfileItem("https://cdn-icons-png.flaticon.com/512/3135/3135715.png", bundle.getString("name"), bundle.getString("email"), bundle.getString("password"), bundle.getString("phone"), "Malaysia", "Undefined", "20/10/2000", false, 100);
                DataManager.getInstance().addProfile(newProfile);
                DataManager.getInstance().save(getContext());
                getActivity().finish();
            }
        });
        return view;
    }

    private void addOTPListener(EditText[] otpEText) {
        for (int i = 0; i < otpEText.length; i++) {
            int finalI = i;
            otpEText[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 0 && before == 1 && finalI > 0) {
                        otpEText[finalI - 1].requestFocus();
                    } else if (s.length() == 1 && finalI < otpEText.length - 1) {
                        otpEText[finalI + 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
            otpEText[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
    }

    private void startCountdownTimer() {
        resendStatus = false;
        resendButton.setTextColor(Color.GRAY);

        new CountDownTimer(resendTimer * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resendTimeLeft.setText("" + (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                resendStatus = true;
                resendButton.setTextColor(Color.BLUE);
            }
        }.start();
    }
}