package com.techwizards.wia2007_trash2treasure;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.squareup.picasso.Picasso;

public class ProfileAdapter {
    private ImageView profileImage;
    private TextView profileName;
    private TextView profileEmail;
    private TextView profilePassword;
    private TextView profilePhone;
    private TextView profileAddress;
    private TextView profileGender;
    private TextView profileDOB;
    private SwitchCompat toggleNotification;
    private Button editProfileButton;
    private Button logOutButton;

    public ProfileAdapter(View rootView) {
        profileImage = rootView.findViewById(R.id.IVProfileImage);
        profileName = rootView.findViewById(R.id.TVProfileName);
        profileEmail = rootView.findViewById(R.id.TVProfileEmail);
        profilePassword = rootView.findViewById(R.id.TVProfilePassword);
        profilePhone = rootView.findViewById(R.id.TVProfilePhone);
        profileAddress = rootView.findViewById(R.id.TVProfileAddress);
        profileGender = rootView.findViewById(R.id.TVProfileGender);
        profileDOB = rootView.findViewById(R.id.TVProfileDOB);
        toggleNotification = rootView.findViewById(R.id.ToggleProfileNotification);
        editProfileButton = rootView.findViewById(R.id.BtnProfileEdit);
        logOutButton = rootView.findViewById(R.id.BtnProfileLogOut);
    }

    public void populateViews(ProfileItem userProfile) {
        Picasso.get().load(userProfile.getImagePath()).error(R.drawable.ic_launcher_foreground).into(profileImage);
        profileName.setText(userProfile.getName());
        profileEmail.setText(userProfile.getEmail());
        profilePassword.setText(userProfile.maskedPassword());
        profilePhone.setText(userProfile.getPhone());
        profileAddress.setText(userProfile.getAddress());
        profileGender.setText(userProfile.getGender());
        profileDOB.setText(userProfile.getDateOfBirth());
        toggleNotification.setChecked(userProfile.isAllowNoti());
    }
}
