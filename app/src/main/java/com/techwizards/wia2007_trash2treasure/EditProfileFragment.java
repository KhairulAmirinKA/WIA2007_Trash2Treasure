package com.techwizards.wia2007_trash2treasure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    DataManager dataManager = DataManager.getInstance();

    private ProfileAdapter_ForEdit profileAdapterForEdit;
    private ProfileItem userProfile;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
            public void onClick(View view) {
                //save new profile
                saveNewProfle(view);
            }
        });


        return view;
    }

    //when user clicks Save btn
    private void saveNewProfle(View view) {

        EditText ETEditProfileName = getView().findViewById(R.id.ETEditProfileName);
        EditText ETEditProfileEmail = getView().findViewById(R.id.ETEditProfileEmail);
        EditText ETEditProfilePassword = getView().findViewById(R.id.ETEditProfilePassword);
        EditText ETEditPhone = getView().findViewById(R.id.ETEditPhone);
        EditText ETEditAddress = getView().findViewById(R.id.ETEditAddress);
        EditText ETEditDob = getView().findViewById(R.id.ETEditDob);

        String newImagePath= "https://icon-library.com/images/admin-user-icon/admin-user-icon-4.jpg";
        String newName= ETEditProfileName.getText().toString();
        String newEmail = ETEditProfileEmail.getText().toString();
        //String newPassword= ETEditProfilePassword.getText().toString();
        String newPhone= ETEditPhone.getText().toString();
        String newAddress = ETEditAddress.getText().toString();
        String newGender= "Male";
        String newDob= ETEditDob.getText().toString();
        boolean newAllowNoti=true;
       // int newPoint= 900;

        //testing
        Toast.makeText(getActivity().getApplicationContext(),
                newName+ newEmail+ newAddress+ newPhone+ newDob, Toast.LENGTH_SHORT).show();

        //generate new profile
        ProfileItem updatedProfile = new ProfileItem(newImagePath, newName, newEmail,
                newPhone, newAddress, newGender, newDob, newAllowNoti);

        FirebaseService firebaseService = FirebaseService.getInstance();

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("user_profiles")
                .document( updatedProfile.getEmail() )
                .set(updatedProfile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity().getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                    //go to profile page
                    Navigation.findNavController(view).navigate(R.id.DestProfile);
                    }
                });






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