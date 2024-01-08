package com.techwizards.wia2007_trash2treasure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    DataManager dataManager = DataManager.getInstance();

    private ProfileAdapter_ForEdit profileAdapterForEdit;
    private ProfileItem userProfile;

    public EditProfileFragment() {}

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
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
        View view=  inflater.inflate(R.layout.fragment_edit_profile, container, false);

        //fetch data
        profileAdapterForEdit = new ProfileAdapter_ForEdit(view);
        userProfile = dataManager.currentUser.getCurrentUser();
        profileAdapterForEdit.populateViews_ForEdit(userProfile);

        //cancel btn
        Button BtnCancelEditProfile = view.findViewById(R.id.BtnCancelEditProfile);
        BtnCancelEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //yes or no to cancel the editing
                showConfirmationDialog(view);
            }
        });

        //save btn
        Button BtnSaveNewProfile = view.findViewById(R.id.BtnSaveNewProfile);
        BtnSaveNewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save new profile
                saveNewProfile(view);
                Navigation.findNavController(view).popBackStack();
            }
        });

        return view;
    }

    //when user clicks Save btn
    private void saveNewProfile(View view) {
        ProfileItem currentProfile = dataManager.currentUser.getCurrentUser();

        EditText ETEditProfileName = view.findViewById(R.id.ETEditProfileName);
        EditText ETEditProfileEmail = view.findViewById(R.id.ETEditProfileEmail);
        EditText ETEditProfilePassword = view.findViewById(R.id.ETEditProfilePassword);
        EditText ETEditPhone = view.findViewById(R.id.ETEditPhone);
        EditText ETEditAddress = view.findViewById(R.id.ETEditAddress);
        RadioGroup RGGender = view.findViewById(R.id.RGEditGender);
        EditText ETEditDob = view.findViewById(R.id.ETEditDob);
        SwitchCompat SCEditAllowNoti = view.findViewById(R.id.ToggleEditProfileNotification);

        String newImagePath= "https://icon-library.com/images/admin-user-icon/admin-user-icon-4.jpg";
        String newName = ETEditProfileName.getText().toString();
        String newEmail = ETEditProfileEmail.getText().toString();
        String newPassword= ETEditProfilePassword.getText().toString();
        String newPhone= ETEditPhone.getText().toString();
        String newAddress = ETEditAddress.getText().toString();

        int buttonId = RGGender.getCheckedRadioButtonId();
        String newGender = "";

        if (buttonId == R.id.RBEditMale) {
            newGender = "Male";
        } else if (buttonId == R.id.RBEditFemale) {
            newGender = "Female";
        }

        String newDob= ETEditDob.getText().toString();
        boolean newAllowNoti = SCEditAllowNoti.isChecked();
        int newPoint = currentProfile.getPoints();

        //generate new profile
        ProfileItem updatedProfile = new ProfileItem(newImagePath, newName, newEmail, newPassword.equals(currentProfile.getPasswords().get("plain")) ? currentProfile.getPasswords().get("plain") : newPassword, newPhone, newAddress, newGender, newDob, newAllowNoti, newPoint);

        dataManager.updateProfile(updatedProfile);
        dataManager.save(getContext());
        //testing
        Toast.makeText(getContext(),
                "Update Success", Toast.LENGTH_SHORT).show();

//        FirebaseService firebaseService = FirebaseService.getInstance();

        //save new profile
//        firebaseService.saveUserProfile(updatedProfile, new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if (task.isSuccessful()){
//                    Toast.makeText(getActivity().getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
//
//                    //go to profile page
//                    Navigation.findNavController(view).navigate(R.id.DestProfile);
//
//                }
//                else {
//                    Toast.makeText(getActivity().getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        //update profile
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("user_profiles")
//                .document( updatedProfile.getEmail() )
//                .set(updatedProfile)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(getActivity().getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
//
//                    //go to profile page
//                    Navigation.findNavController(view).navigate(R.id.DestProfile);
//                    }
//                });
    } //save


    //when users click cancel editing, dialog box will appear
    private void showConfirmationDialog(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext() );

        builder.setTitle("Cancel Editing");

        builder.setMessage("Are you sure you want to cancel editing? Any unsaved changes will be lost.");

        //yes btn
        String positiveText= "Yes";
        SpannableString spannableString = new SpannableString(positiveText);

        //yes btn to become red
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, positiveText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setPositiveButton(spannableString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //go back to profile page
                Navigation.findNavController(view).navigate(R.id.DestProfile);
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