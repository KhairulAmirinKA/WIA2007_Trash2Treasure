package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class ReportDetails extends Fragment {
    public static final String POSITION = "POSITION";

    TextView TVReportDetailsStatus, TVReportDetailsTitle,TVReportDetailsType,TVReportDetailsTime,TVReportDetailsDate;
    TextView TVReportDetailsAddress, TVReportDetailsPIC, TVReportDetailsDesc;
    TextView TVReportDetailsLastUpdated;
    ImageView IVReportDetailsImg;

    public ReportDetails() {
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
        View view= inflater.inflate(R.layout.fragment_report_details, container, false);

        //back btn
        MaterialIconView BtnDismiss= view.findViewById(R.id.BtnDismiss);
        BtnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        //retrive data from ReportAdapter
        int position=0;

        Bundle bundle = getArguments();

        if (bundle!= null && bundle.containsKey(POSITION)){
            //dah ada position, boleh guna data
            position = bundle.getInt(POSITION, 0);
        }

        ReportItem currentItem = new Report().generateReportItems().get(position);

        //init UI
        initView(view);

        //set the text
        TVReportDetailsStatus.setText( currentItem.getStatus() );
        TVReportDetailsTitle.setText(currentItem.getReportTitle() );

        TVReportDetailsType.setText("Type: "+ currentItem.getReportType() );
        TVReportDetailsAddress.setText("Address: "+ currentItem.getAddress() );
        TVReportDetailsTime.setText("Time: "+currentItem.getReportTime() );
        TVReportDetailsDate.setText("Date: "+ currentItem.getReportDate());

        String[] names={"Mr Ali","Mr Ravindran", "Mr Yangding", "Miss Ameera", "Miss Illya", "Mr Khairul"};
        int N = names.length;

        TVReportDetailsPIC.setText("Person in charge: "+ names[position%N]);
        TVReportDetailsDesc.setText( currentItem.getReportDescription() );

        //get current time
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String timestamp= dateFormat.format(calendar.getTime());

        TVReportDetailsLastUpdated.setText("Last Updated:"+ timestamp );

        //setting the img
        String imgPath= currentItem.getImgPath();
        System.out.println(imgPath);

        if (!imgPath.equals("NA")){
            Log.d("DownloadURL", imgPath);

        Glide.with(requireContext())
                .load(imgPath)
                .into(IVReportDetailsImg);

        //Picasso.get().load(imgPath).into(IVReportDetailsImg);
        }




        return view;
    }

    private void initView(View view) {
        TVReportDetailsStatus = view.findViewById(R.id.TVReportDetailsStatus);
        TVReportDetailsTitle = view.findViewById(R.id.TVReportDetailsTitle);
        TVReportDetailsType = view.findViewById(R.id.TVReportDetailsType);
        TVReportDetailsAddress = view.findViewById(R.id.TVReportDetailsAddress);

        TVReportDetailsTime = view.findViewById(R.id.TVReportDetailsTime);
        TVReportDetailsDate = view.findViewById(R.id.TVReportDetailsDate);
        TVReportDetailsPIC = view.findViewById(R.id.TVReportDetailsPIC);
        TVReportDetailsDesc = view.findViewById(R.id.TVReportDetailsDesc);

        IVReportDetailsImg = view.findViewById(R.id.IVReportDetailsImg);
        TVReportDetailsLastUpdated = view.findViewById(R.id.TVReportDetailsLastUpdated);
    }
}