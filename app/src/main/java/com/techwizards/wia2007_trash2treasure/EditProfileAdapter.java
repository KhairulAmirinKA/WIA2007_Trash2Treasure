package com.techwizards.wia2007_trash2treasure;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.appcompat.widget.SwitchCompat;

import com.squareup.picasso.Picasso;

public class EditProfileAdapter {
    ImageView IVEditProfileImage;
    EditText ETEditProfileName;
    EditText ETEditProfileEmail;
    EditText ETEditProfilePassword;
    EditText ETEditPhone;
    EditText ETEditAddress;
    RadioGroup RGGender;
    EditText ETEditDob;
    SwitchCompat ToggleEditProfileNotification;

    public EditProfileAdapter(View rootView) {

        IVEditProfileImage= rootView.findViewById(R.id.IVEditProfileImage);

        ETEditProfileName = rootView.findViewById(R.id.ETEditProfileName);
        ETEditProfileEmail = rootView.findViewById(R.id.ETEditProfileEmail);
        ETEditProfilePassword = rootView.findViewById(R.id.ETEditProfilePassword);
        ETEditPhone = rootView.findViewById(R.id.ETEditPhone);
        ETEditAddress = rootView.findViewById(R.id.ETEditAddress);
        RGGender = rootView.findViewById(R.id.RGEditGender);
        ETEditDob = rootView.findViewById(R.id.ETEditDob);

        ToggleEditProfileNotification = rootView.findViewById(R.id.ToggleEditProfileNotification);
    }

    public void populateViews(ProfileItem userProfile) {
        Picasso.get().load(userProfile.getImagePath()).error(R.drawable.ic_launcher_foreground).into(IVEditProfileImage);
        ETEditProfileName.setText(userProfile.getName());
        ETEditProfileEmail.setText(userProfile.getEmail());
        ETEditProfilePassword.setText(userProfile.getPasswords().get("plain"));
        ETEditPhone.setText(userProfile.getPhone());
        ETEditAddress.setText(userProfile.getAddress());

        if (userProfile.getGender().equals("Male")) {
            RGGender.check(R.id.RBEditMale);
        } else if (userProfile.getGender().equals("Female")) {
            RGGender.check(R.id.RBEditFemale);
        }

        ETEditDob.setText(userProfile.getDateOfBirth());
        ToggleEditProfileNotification.setChecked(userProfile.isAllowNoti());
    }
}
