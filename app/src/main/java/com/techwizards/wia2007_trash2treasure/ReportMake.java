package com.techwizards.wia2007_trash2treasure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportMake extends Fragment {

    private static final int REQUEST_CODE_PICK_PHOTO = 101;
    private Uri selectedPhotoUri;
    StorageReference imgRef;
    String fileName;
    String imgPath="NA";

    String[] options;
    Spinner SpinnerReportLocalAuthority;
    RadioGroup RGReportType;
    EditText ETReportDescription;
    EditText ETReportAddress;

    Button BtnReportUploadImg;
    TextView TVReportPhotoName;

    DataManager dataManager = DataManager.getInstance();
    public ReportMake() {
        // Required empty public constructor
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

        RGReportType = view.findViewById(R.id.RGReportMakeType);
        ETReportDescription = view.findViewById(R.id.ETReportDescription);
        ETReportAddress = view.findViewById(R.id.ETReportAddress);
        BtnReportUploadImg= view.findViewById(R.id.BtnReportUploadImg);
        TVReportPhotoName= view.findViewById(R.id.TVReportPhotoName);

        //map
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Maps maps = new Maps();
        fragmentTransaction.replace(R.id.FCVComplaintLocation, maps);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        //upload photo from local device
        BtnReportUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto(view);
            }
        });



        //clicks the submit btn
        Button btnSubmit = view.findViewById(R.id.BtnReportSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //createReport will returns true if the report form is complete
                if (createReport()){

                    //once the reports are submitted to the db, user will go back to Report page
                    //otherwise, they will stay in New Report section
                    Navigation.findNavController(view).popBackStack();
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_CODE_PICK_PHOTO && resultCode==Activity.RESULT_OK){

            //handle selected photo
            selectedPhotoUri= data.getData();

            //uploadToFirebaseStorage();
        }
    }

    private void uploadToFirebaseStorage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef=storage.getReference();

        //get current time
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_HHmm", Locale.getDefault());
        String timestamp= dateFormat.format(calendar.getTime());

        //bina nama file
        fileName= dataManager.currentUser.getCurrentUser()+"_"+ timestamp;

         imgRef= storageRef.child("reportPhotos/" + fileName);

        imgRef.putFile(selectedPhotoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageRef.child("reportPhotos/"+fileName).getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imgPath= uri.toString();
                            }
                        });

                        if (isAdded()) { //checks whether fragment is attached to the activity
                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        if (isAdded()) {
                            Toast.makeText(getContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                            Log.e("GAGAL", e.getMessage());
                        }
                    }
                });



    }

    private boolean createReport() {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");

        //get the index of the authority
        int indexSpinner = SpinnerReportLocalAuthority.getSelectedItemPosition();
        String localAuth = options[indexSpinner];

        Pattern pattern = Pattern.compile("[a-z]");
        Matcher matcher = pattern.matcher(localAuth.replace(" ", ""));
        String title = date.format(new Date()).split("/")[2] + "/" + matcher.replaceAll("") + "/10/13/0015-KDSA-" + getIndexForLocalAuth(localAuth);

        //get id of the checked RadioButton
        int indexType = RGReportType.getCheckedRadioButtonId();
        String reportType = "";

        if (indexType == R.id.RBReportComplaint) {
            reportType = "Complaint";
        } else if (indexType == R.id.RBReportSuggestion) {
            reportType = "Suggestion";
        }

        //checks if the EditText is empty
        //checks if reportType (value from RadioButton) is empty
        if (!TextUtils.isEmpty(ETReportDescription.getText()) && !TextUtils.isEmpty(ETReportAddress.getText()) && !reportType.equals("")) {
            //extract the String from editText
            String description = ETReportDescription.getText().toString();
            String address = ETReportAddress.getText().toString();

            //upload photo to firebase storage
            if (selectedPhotoUri!= null){
                uploadToFirebaseStorage();
            }

//            final String[] imgPath = {"NA"};
//
//            imgRef.child(imgRef.getPath()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()){
//                        Uri downloadUrl= task.getResult();
//
//                        imgPath[0] = downloadUrl.toString();
//                    }
//                }
//            });

            ReportItem newReport = new ReportItem(localAuth, title, reportType, description,
                    address, dataManager.currentUser.getCurrentUser().getName(), "Pending",
                    date.format(new Date()), time.format(new Date()) , imgPath);

            //add to the firebase
            dataManager.addNewReport(newReport);

            Toast.makeText(getContext(), "Report is successfully submitted", Toast.LENGTH_SHORT).show();

            return true;
        }

        else {
            //this means the report form is not complete. will not send to the database
            Toast.makeText(getContext(), "Please provide the details", Toast.LENGTH_SHORT).show();
        }

        return false; //false means the report will not submitted to the db
    }

    private int getIndexForLocalAuth(String localAuth) {
        int count = 0;
        for (ReportItem item : dataManager.reportItems) {
            if (item.getLocalAuth().contains(localAuth)) {
                count++;
            }
        }
        return count + 1;
    }

    //handle the name local authority in dropdown spinner
    private void handleLocalAuthoritySpinner(View view) {
        //get spinner
        SpinnerReportLocalAuthority = view.findViewById(R.id.SpinnerReportLocalAuthority);

        options = new String[]{"Majlis Bandaraya Shah Alam", "Majlis Bandaraya Petaling Jaya", "Majlis Bandaraya Kuala Selangor"};

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

    private void uploadPhoto(View view){
        ImagePicker.Companion.with(this)
                .crop()
                .start(REQUEST_CODE_PICK_PHOTO);

        TVReportPhotoName.setText("Successfully chose photo");
        TVReportPhotoName.setVisibility(View.VISIBLE);
    }
}