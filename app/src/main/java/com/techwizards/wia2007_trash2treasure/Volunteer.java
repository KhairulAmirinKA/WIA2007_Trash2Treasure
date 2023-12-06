package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Volunteer extends Fragment {

    private VolunteerAdapter volunteerAdapter;
    private Spinner categories;
    TextView TVNoProjects;

    public Volunteer() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_volunteer, container, false);

        TVNoProjects = view.findViewById(R.id.TVVolunteerNoProjects);

        categories = view.findViewById(R.id.SVolunteerCategories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.volunteer_categories, R.layout.volunteer_categories_item_row_view);
        adapter.setDropDownViewResource(R.layout.volunteer_categories_item_row_view);
        categories.setAdapter(adapter);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_volunteer_item);
        volunteerAdapter = new VolunteerAdapter(generateVolunteerList());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(volunteerAdapter);

        RadioGroup radioGroup = view.findViewById(R.id.RGVolunteer);

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateAdapterData();

                updateRadioButtonListener(radioGroup);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                updateAdapterData();

                updateRadioButtonListener(radioGroup);
            }
        });
        categories.setSelected(true);

        return view;
    }

    private void updateRadioButtonListener(RadioGroup radioGroup) {
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
    }

    private List<VolunteerItem> generateVolunteerList() {
        List<VolunteerItem> volunteerItems = new ArrayList<>();

        volunteerItems.add(new VolunteerItem(
                "https://apicms.thestar.com.my/uploads/images/2023/02/25/1955922.jpg",
                "Community Recycling Drive",
                "Help organize and participate in a community recycling drive to collect and process recyclable materials.",
                20,
                "2023-12-15",
                "2024-02-15",
                "10:00 AM - 2:00 PM",
                12,
                "Local Area, Recreational Area"
        ));

        volunteerItems.add(new VolunteerItem(
                "https://cdn.cseindia.org/large/2021-08-09/0.75503400_1628496890_solid-wastebanners.jpg",
                "Waste Sorting Workshop",
                "Educate the community on proper waste sorting techniques. Assist in organizing and conducting the workshop.",
                15,
                "2024-03-10",
                "2024-03-12",
                "2:00 PM - 5:00 PM",
                31,
                "Local Area, Educational"
        ));

        volunteerItems.add(new VolunteerItem(
                "https://cleanmalaysia.com/wp-content/uploads/2016/02/p2_mprk_dz_0102_p2a_dz_6.jpg",
                "River Cleanup Campaign",
                "Join the efforts to clean up a local riverbank by collecting and properly disposing of waste materials to promote a cleaner environment.",
                25,
                "2023-11-22",
                "2024-04-22",
                "9:00 AM - 1:00 PM",
                40,
                "Marine"
        ));

        volunteerItems.add(new VolunteerItem(
                "https://apicms.thestar.com.my/uploads/images/2021/02/07/1036152.jpg",
                "E-Waste Collection Event",
                "Contribute to the proper disposal of electronic waste by assisting in collecting and organizing e-waste items for recycling.",
                18,
                "2023-11-20",
                "2023-11-25",
                "11:00 AM - 3:00 PM",
                25,
                "Local Area"
        ));

        volunteerItems.add(new VolunteerItem(
                "https://apicms.thestar.com.my/uploads/images/2022/10/04/1762165.jpg",
                "Composting Awareness Workshop",
                "Promote sustainable practices by participating in a composting workshop. Learn and teach composting techniques to reduce organic waste.",
                12,
                "2023-11-15",
                "2023-12-15",
                "3:00 PM - 6:00 PM",
                18,
                "Educational"
        ));

        return volunteerItems;
    }

    private void filterVolunteerList(Filter filter) {
        TVNoProjects.setVisibility(View.GONE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<VolunteerItem> filteredList = new ArrayList<>();
        Date today;
        try {
            today = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<VolunteerItem> volunteerItems = applyCategoryFilter(generateVolunteerList());

        for (VolunteerItem volunteerItem : volunteerItems) {
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

        if (filteredList.isEmpty() || volunteerItems.isEmpty()) {
            String selectedCategory = categories.getSelectedItem().toString();
            String filterText = getFilterText(filter);
            String message = "No projects available in " + selectedCategory + ": " + filterText;
            TVNoProjects.setText(message);
            TVNoProjects.setVisibility(View.VISIBLE);
        } else {
            TVNoProjects.setVisibility(View.GONE);
        }

        volunteerAdapter.updateList(filteredList);
    }

    private enum Filter {
        TRENDING, UPCOMING, ONGOING
    }

    private String getFilterText(Filter filter) {
        switch (filter) {
            case TRENDING:
                return "Trending";
            case UPCOMING:
                return "Upcoming";
            case ONGOING:
                return "Ongoing";
            default:
                return "";
        }
    }

    private void updateAdapterData() {
        List<VolunteerItem> filteredData = applyCategoryFilter(generateVolunteerList());
        volunteerAdapter.updateList(filteredData);
    }

    private List<VolunteerItem> applyCategoryFilter(List<VolunteerItem> volunteerItems) {
        int selectedPosition = categories.getSelectedItemPosition();
        if (selectedPosition == 0) {
            return new ArrayList<>(volunteerItems);
        } else {
            String selectedCategory = getResources().getStringArray(R.array.volunteer_categories)[selectedPosition];
            List<VolunteerItem> filteredCategoriesItem = new ArrayList<>();
            for (VolunteerItem item : volunteerItems) {
                if (item.getVolunterCategories().contains(selectedCategory)) {
                    filteredCategoriesItem.add(item);
                }
            }

            return filteredCategoriesItem;
        }
    }
}

