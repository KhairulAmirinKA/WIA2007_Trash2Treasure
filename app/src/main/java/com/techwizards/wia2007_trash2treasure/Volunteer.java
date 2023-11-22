package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Volunteer extends Fragment {

    private VolunteerAdapter volunteerAdapter;

    public Volunteer() {
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
        View view = inflater.inflate(R.layout.fragment_volunteer, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_volunteer_item);
        volunteerAdapter = new VolunteerAdapter(generateVolunteerList());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(volunteerAdapter);

        RadioGroup radioGroup = view.findViewById(R.id.RGVolunteer);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.RBVTrending) {
                filterVolunteerList(Filter.TRENDING);
            } else if (checkedId == R.id.RBVUpcoming) {
                filterVolunteerList(Filter.UPCOMING);
            } else if (checkedId == R.id.RBVOngoing) {
                filterVolunteerList(Filter.ONGOING);
            }
        });

        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

        return view;
    }

    private List<VolunteerItem> generateVolunteerList() {
        List<VolunteerItem> volunteerItems = new ArrayList<>();

        volunteerItems.add(new VolunteerItem(
                "recycling_drive_image.jpg",
                "Community Recycling Drive",
                "Help organize and participate in a community recycling drive to collect and process recyclable materials.",
                20,
                "2023-12-15",
                "2024-02-15",
                "10:00 AM - 2:00 PM",
                12
        ));

        volunteerItems.add(new VolunteerItem(
                "waste_sorting_workshop_image.jpg",
                "Waste Sorting Workshop",
                "Educate the community on proper waste sorting techniques. Assist in organizing and conducting the workshop.",
                15,
                "2024-03-10",
                "2024-03-12",
                "2:00 PM - 5:00 PM",
                31
        ));

        volunteerItems.add(new VolunteerItem(
                "river_cleanup_image.jpg",
                "River Cleanup Campaign",
                "Join the efforts to clean up a local riverbank by collecting and properly disposing of waste materials to promote a cleaner environment.",
                25,
                "2023-11-22",
                "2024-04-22",
                "9:00 AM - 1:00 PM",
                40
        ));

        volunteerItems.add(new VolunteerItem(
                "e_waste_collection_image.jpg",
                "E-Waste Collection Event",
                "Contribute to the proper disposal of electronic waste by assisting in collecting and organizing e-waste items for recycling.",
                18,
                "2023-11-20",
                "2023-11-25",
                "11:00 AM - 3:00 PM",
                25
        ));

        volunteerItems.add(new VolunteerItem(
                "composting_workshop_image.jpg",
                "Composting Awareness Workshop",
                "Promote sustainable practices by participating in a composting workshop. Learn and teach composting techniques to reduce organic waste.",
                12,
                "2023-11-15",
                "2023-12-15",
                "3:00 PM - 6:00 PM",
                18
        ));

        return volunteerItems;
    }

    private void filterVolunteerList(Filter filter) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<VolunteerItem> filteredList = new ArrayList<>();
        Date today;
        try {
            today = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for (VolunteerItem volunteerItem : generateVolunteerList()) {
            switch (filter) {
                case TRENDING:
                    try {
                        Date startDate = sdf.parse(volunteerItem.getVolunteerStartDate());
                        Date endDate = sdf.parse(volunteerItem.getVolunteerEndDate());
                        if (volunteerItem.getVolunteerParticipantCount() > 25 && today.after(startDate) && today.before(endDate)) {
                            filteredList.add(volunteerItem);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPCOMING:
                    try {
                        Date startDate = sdf.parse(volunteerItem.getVolunteerStartDate());
                        if (today.compareTo(startDate) < 0) {
                            filteredList.add(volunteerItem);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case ONGOING:
                    try {
                        Date startDate = sdf.parse(volunteerItem.getVolunteerStartDate());
                        Date endDate = sdf.parse(volunteerItem.getVolunteerEndDate());
                        if (today.compareTo(endDate) < 0 && today.compareTo(startDate) > 0) {
                            filteredList.add(volunteerItem);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        volunteerAdapter.updateList(filteredList);
    }

    private enum Filter {
        TRENDING, UPCOMING, ONGOING
    }
}

