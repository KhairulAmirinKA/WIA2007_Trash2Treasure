package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class EducationArticles extends Fragment {
    public static final String POSITION = "POSITION";

    //String title;

    public EducationArticles() {
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
        View view= inflater.inflate(R.layout.fragment_education_articles, container, false);

        // Retrieve data from the arguments bundle
        int position=0;

        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(POSITION)) {
             position = bundle.getInt(POSITION, 0);
            // Now you have the data in the receivedData variable
            // You can use it as needed
        }


        TextView TVEduArticleTitle= view.findViewById(R.id.TVEduArticleTitle);

        EducationResources educationResources = new EducationResources();

        EducationItem currentItem= educationResources.generateEduList().get(position);

        //set title
        TVEduArticleTitle.setText( currentItem.getEduTitle());

        return view;
    }
}