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
import java.util.UUID;

public class Volunteer extends Fragment {

    private VolunteerAdapter volunteerAdapter;
    private Spinner categories;
    TextView TVNoProjects;
    RadioGroup radioGroup;
    Filter currentFilter = Filter.TRENDING;

    DataManager dataManager = DataManager.getInstance();

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

        //spinner for categories of volunteer
        categories = view.findViewById(R.id.SVolunteerCategories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.volunteer_categories, R.layout.text_only_item_view);
        adapter.setDropDownViewResource(R.layout.text_only_item_view);
        categories.setAdapter(adapter);

        categories.setSelection(0); //default selection

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_volunteer_item);
        volunteerAdapter = new VolunteerAdapter(generateVolunteerList());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(volunteerAdapter);

        radioGroup = view.findViewById(R.id.RGVolunteer);
        updateAdapterData();

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterVolunteerList(currentFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateRadioButtonListener();

        return view;
    }

    private void updateRadioButtonListener() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.RBVTrending) {
                currentFilter = Filter.TRENDING;
            } else if (checkedId == R.id.RBVUpcoming) {
                currentFilter = Filter.UPCOMING;
            } else if (checkedId == R.id.RBVOngoing) {
                currentFilter = Filter.ONGOING;
            } else if (checkedId==R.id.RBVEnrolled){
                currentFilter = Filter.ENROLLED;
            }
            filterVolunteerList(currentFilter);
        });

        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
    }

    public List<VolunteerItem> generateVolunteerList() {
        //List<VolunteerItem> volunteerItems = dataManager.volunteerItems;
        List<VolunteerItem> volunteerItems = new ArrayList<>();

        System.out.println(volunteerItems); //ni berjaya
        System.out.println(volunteerItems.size()); //ni berjaya

        System.out.println("LINE 110");
        for (VolunteerItem item: dataManager.volunteerItems){
            System.out.println(item.volunteerTitle);
            volunteerItems.add(item);
        }

//        volunteerItems.add(new VolunteerItem(
//                "https://apicms.thestar.com.my/uploads/images/2023/02/25/1955922.jpg",
//                "Community Recycling Drive",
//                "Help organize and participate in a community recycling drive to collect and process recyclable materials.",
//                20,
//                "2024-01-15",
//                "2024-02-15",
//                "10:00 AM - 2:00 PM",
//                12,
//                "Local Area, Recreational Area"
//        ));
//
//        volunteerItems.add(new VolunteerItem(
//                "https://cdn.cseindia.org/large/2021-08-09/0.75503400_1628496890_solid-wastebanners.jpg",
//                "Waste Sorting Workshop",
//                "Educate the community on proper waste sorting techniques. Assist in organizing and conducting the workshop.",
//                15,
//                "2024-03-10",
//                "2024-03-12",
//                "2:00 PM - 5:00 PM",
//                31,
//                "Local Area, Educational"
//        ));
//
//        volunteerItems.add(new VolunteerItem(
//                "https://cleanmalaysia.com/wp-content/uploads/2016/02/p2_mprk_dz_0102_p2a_dz_6.jpg",
//                "River Cleanup Campaign",
//                "Join the efforts to clean up a local riverbank by collecting and properly disposing of waste materials to promote a cleaner environment.",
//                25,
//                "2024-01-12",
//                "2024-04-22",
//                "9:00 AM - 1:00 PM",
//                40,
//                "Marine"
//        ));
//
//        volunteerItems.add(new VolunteerItem(
//                "https://apicms.thestar.com.my/uploads/images/2021/02/07/1036152.jpg",
//                "E-Waste Collection Event",
//                "Contribute to the proper disposal of electronic waste by assisting in collecting and organizing e-waste items for recycling.",
//                18,
//                "2024-01-20",
//                "2024-02-25",
//                "11:00 AM - 3:00 PM",
//                25,
//                "Local Area"
//        ));
//
//        volunteerItems.add(new VolunteerItem(
//                "https://apicms.thestar.com.my/uploads/images/2022/10/04/1762165.jpg",
//                "Composting Awareness Workshop",
//                "Promote sustainable practices by participating in a composting workshop. Learn and teach composting techniques to reduce organic waste.",
//                12,
//                "2024-01-10",
//                "2024-12-15",
//                "3:00 PM - 6:00 PM",
//                18,
//                "Educational"
//        ));
//
//        for (VolunteerItem item : volunteerItems) {
//            dataManager.addNewVolunteer(item);
//        }

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

        //category filter- All, Marine, Wildlife
        //List<VolunteerItem> volunteerItems = applyCategoryFilter(generateVolunteerList());
        List<VolunteerItem> volunteerItems = generateVolunteerList();

        System.out.println("apply filter"+ volunteerItems.size());
        System.out.println("generate"+ generateVolunteerList().size());

        for (VolunteerItem volunteerItem : volunteerItems) {
            switch (filter) {
                case ENROLLED:
                    List<UUID> joinedVolunteer = dataManager.currentUser.getCurrentUser().getJoinedVolunteer();

                    if (joinedVolunteer!= null){
                    for (int i = 0; i < joinedVolunteer.size(); i++) {
                        if (joinedVolunteer.get(i).equals(volunteerItem.id)) {
                            filteredList.add(volunteerItem);
                        }
                    }
                    }
                    break;

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
                        if (today.compareTo(endDate) <=0 && today.compareTo(startDate) >= 0) {
                            filteredList.add(volunteerItem);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        if (filteredList.isEmpty()) {
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
        ENROLLED, TRENDING, UPCOMING, ONGOING
    }

    private String getFilterText(Filter filter) {
        switch (filter) {

            case ENROLLED:
                return "Enrolled";
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
        List<VolunteerItem> filteredData = applyCategoryFilter(dataManager.volunteerItems);
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

