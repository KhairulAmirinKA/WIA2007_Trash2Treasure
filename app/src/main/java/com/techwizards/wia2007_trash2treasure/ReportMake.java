package com.techwizards.wia2007_trash2treasure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportMake extends Fragment {

    DataManager dataManager = DataManager.getInstance();
    private Uri imageUri;
    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            if (o != null) {
                imageUri = o;
            } else {
                Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
            }
        }
    });

    String[] options;
    Spinner SpinnerReportLocalAuthority;
    RadioGroup RGReportType;
    EditText ETReportDescription;
    EditText ETReportAddress;

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

        //map
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        Maps maps = new Maps();
        fragmentTransaction.replace(R.id.FCVComplaintLocation, maps);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Button BtnReportUploadImg = view.findViewById(R.id.BtnReportUploadImg);
        //upload photo from local device
        BtnReportUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
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

    private boolean createReport() {
        String name = dataManager.currentUser.getCurrentUser().getName();

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

            if (imageUri != null) {
                String finalReportType = reportType;
                dataManager.addReportImage(imageUri, new DataManager.ImageUploadCallback() {
                    @Override
                    public void onUploadSuccess(String imageUri) {
                        ReportItem newReport = new ReportItem(localAuth, title, finalReportType, description, address, name, "Pending", date.format(new Date()), time.format(new Date()), imageUri);

                        //add to the firebase
                        dataManager.addNewReport(newReport);
                    }

                    @Override
                    public void unUploadFailure(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            Toast.makeText(getContext(), "Report is successfully submitted", Toast.LENGTH_SHORT).show();

            return true;
        } else {
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

        builder.setMessage("Are you sure you want to discard report? Any un-submitted report will be lost.");

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