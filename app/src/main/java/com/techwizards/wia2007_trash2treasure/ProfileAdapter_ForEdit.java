package com.techwizards.wia2007_trash2treasure;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.SwitchCompat;

import com.squareup.picasso.Picasso;

public class ProfileAdapter_ForEdit {

    ImageView IVEditProfileImage;
    EditText ETEditProfileName;
    EditText ETEditProfileEmail;
    EditText ETEditProfilePassword;
    EditText ETEditPhone;
    EditText ETEditAddress;
    EditText ETEditDob;

    SwitchCompat ToggleEditProfileNotification;


    public ProfileAdapter_ForEdit(View rootView) {

        IVEditProfileImage= rootView.findViewById(R.id.IVEditProfileImage);

        ETEditProfileName = rootView.findViewById(R.id.ETEditProfileName);
        ETEditProfileEmail = rootView.findViewById(R.id.ETEditProfileEmail);
        ETEditProfilePassword = rootView.findViewById(R.id.ETEditProfilePassword);
        ETEditPhone = rootView.findViewById(R.id.ETEditPhone);
        ETEditAddress = rootView.findViewById(R.id.ETEditAddress);
        ETEditDob = rootView.findViewById(R.id.ETEditDob);

        ToggleEditProfileNotification = rootView.findViewById(R.id.ToggleEditProfileNotification);


    }

    public void populateViews_ForEdit(ProfileItem userProfile) {

       Picasso.get().load(userProfile.getImagePath()).error(R.drawable.ic_launcher_foreground).into(IVEditProfileImage);
        ETEditProfileName.setText(userProfile.getName());
        ETEditProfileEmail.setText(userProfile.getEmail());
        ETEditProfilePassword.setText(userProfile.getUnhashPassword());
        ETEditPhone.setText(userProfile.getPhone());
        ETEditAddress.setText(userProfile.getAddress());
       // profileGender.setText(userProfile.getGender());
        ETEditDob.setText(userProfile.getDateOfBirth());
        ToggleEditProfileNotification.setChecked(userProfile.isAllowNoti());
    }
}
