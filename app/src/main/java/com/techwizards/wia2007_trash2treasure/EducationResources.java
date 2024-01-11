package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.List;


public class EducationResources extends Fragment {

    private EducationAdapter educationAdapter;


    public EducationResources() {
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
        View view=  inflater.inflate(R.layout.fragment_education_resources, container, false);

        //back btn
        MaterialIconView BtnDismiss= view.findViewById(R.id.BtnDismiss);
        BtnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        //handling adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_edu_item);
        educationAdapter = new EducationAdapter(generateEduList() );

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity() );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(educationAdapter);

        return view;
    }

    private List<EducationItem> generateEduList() {

        List<EducationItem> educationItems = new ArrayList<>();

        educationItems.add( new EducationItem(
                "https://apicms.thestar.com.my/uploads/images/2023/02/25/1955922.jpg",
                "Global Warming",
                "bumi makin panas. huhu"
        ));

        educationItems.add( new EducationItem(
                "https://apicms.thestar.com.my/uploads/images/2023/02/25/1955922.jpg",
                "Why Recycling?",
                "sebab boleh dik"
        ));

        educationItems.add( new EducationItem(
                "https://apicms.thestar.com.my/uploads/images/2023/02/25/1955922.jpg",
                "Benefits of Reusing",
                "selamatkan bumi"
        ));


        return educationItems;
    }
}