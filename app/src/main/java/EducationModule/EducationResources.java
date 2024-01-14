package EducationModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techwizards.wia2007_trash2treasure.R;

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

    public List<EducationItem> generateEduList() {

        List<EducationItem> educationItems = new ArrayList<>();

        educationItems.add( new EducationItem(
                "https://apicms.thestar.com.my/uploads/images/2023/02/25/1955922.jpg",
                "Global Warming",
                "Understanding the Impact of Global Warming on Waste Management",
                "Global warming, driven by the increase in greenhouse gas emissions, poses significant challenges to our planet. In the realm of waste management, its effects are becoming increasingly evident.\n" +
                        "\n" +
                        "1. Prolonged Landfill Lifespan:\n" +
                        "As temperatures rise, organic waste in landfills decomposes at a faster rate, contributing to the production of methane – a potent greenhouse gas. This not only accelerates landfill depletion but also intensifies the impact on climate change.\n" +
                        "\n" +
                        "2. Changing Waste Composition:\n" +
                        "Shifts in weather patterns and temperatures influence consumer behavior and lifestyles. These changes result in variations in waste composition, with an increase in certain types of waste, such as single-use plastics, contributing to environmental degradation.\n"
        ));

        educationItems.add( new EducationItem(
                "https://theethicalist.com/wp-content/uploads/2023/03/shutterstock_742611010.jpg",
                "Why Recycling?",
                "Let's recycle today for a better tomorrow",
                "Recycling isn't just a task; it's a crucial step towards a sustainable future. It's a collective responsibility that each of us holds to make a positive impact on our planet.\n" +
                        "\n" +
                        "Benefits of Recycling:\n" +
                        "Conserving Resources: Recycling reduces the demand for raw materials, conserving valuable resources and energy required for manufacturing.\n" +
                        "Reducing Landfill Waste: By diverting items from landfills, recycling minimizes the environmental impact of waste disposal.\n" +
                        "Curbing Pollution: Recycling helps mitigate pollution caused by the extraction and processing of raw materials."
        ));

        educationItems.add( new EducationItem(
                "https://i0.wp.com/maximizeminimalism.com/wp-content/uploads/2019/09/25-Ways-To-Reuse-Common-Household-Items.png?fit=1000%2C800&ssl=1",
                "Benefits of Reusing",
                "Join the reuse revolution for a greener, more sustainable future! ",
                "In a society driven by disposability, reusing items becomes a revolutionary act. It's not just about prolonging the life of your favorite mug or tote bag; it's a commitment to reducing the environmental footprint we leave behind.\n" +
                        "Benefits of Reusing:\n" +
                        "Waste Reduction: Reusing items reduces the volume of waste that ends up in landfills. From clothing to containers, giving things a second life lessens our impact on the planet.\n" +
                        "Conservation of Resources: Every reused item means one less new item needs to be produced. This conserves the raw materials and energy required for manufacturing, contributing to a more sustainable future.\n" +
                        "Cost Savings: While not the primary focus, reusing can save you money. Choosing second-hand or repurposed items often comes at a fraction of the cost of buying new."
        ));

        educationItems.add( new EducationItem(
                "https://popedouglasrecycle.com/wp-content/uploads/2021/02/pope.png",
                "The ABCs of Waste Reduction: A Beginner's Guide",
                "Dive into waste reduction with our beginner's guide!",
                "Welcome to the world of waste reduction! If you're just starting, here's a quick guide to get you going.\n" +
                        "\n" +
                        "A - Avoid Single-Use Plastics: Swap disposable items with reusable alternatives. From bags to bottles, small changes make a big difference.\n" +
                        "\n" +
                        "B - Be Mindful of Packaging: Choose products with minimal packaging or opt for eco-friendly packaging. Your choices impact the environment.\n" +
                        "\n" +
                        "C - Compost Kitchen Scraps: Turn food scraps into nutrient-rich compost. It's an easy way to reduce landfill waste and nourish your garden.\n" +
                        "Start with these ABCs, and you'll be on your way to a more sustainable lifestyle. Happy reducing!"
        ));

        educationItems.add( new EducationItem(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKzI_qwSWC9usgj5wt5QzGFqf5kJuEdIH9kg&usqp=CAU",
                "The Carbon Footprint: Reducing Your Impact",
                "Understand how everyday choices impact the environment",
                "The concept of a carbon footprint often feels abstract, but its implications on our planet are very real. Understanding and minimizing our individual carbon footprints is a powerful step towards a more sustainable future.\n" +
                        "\n" +
                        "A carbon footprint is the total amount of greenhouse gases, particularly carbon dioxide, that an individual, organization, or product is responsible for emitting. It encompasses daily activities, energy consumption, and even the products we choose.\n" +
                        "Reducing Your Impact:\n" +
                        "Energy Efficiency: Opt for energy-efficient appliances and lighting. Simple choices like turning off lights when not needed make a significant difference.\n" +
                        "Sustainable Transportation: Consider walking, cycling, or using public transport. If possible, explore electric or hybrid vehicles for a greener commute.\n" +
                        "Mindful Consumption: Make informed choices about what you buy. Support products with eco-friendly certifications and those produced with sustainability in mind."
        ));

        educationItems.add( new EducationItem(
                "https://www.shutterstock.com/shutterstock/photos/1305900268/display_1500/stock-photo-zero-waste-shopping-and-sustanable-lifestyle-concept-various-farm-organic-vegetables-grains-1305900268.jpg",
                "Tips for a Zero-Waste Kitchen",
                "Discover the art of a zero-waste kitchen with these tips!",
                "In the pursuit of a sustainable lifestyle, minimizing food waste is a crucial step. Here are some tips for cultivating a zero-waste kitchen:\n" +
                        "Proper Storage: Store perishables appropriately to prolong freshness. Utilize airtight containers, keep fruits and vegetables separate, and understand the storage needs of different items.\n" +
                        "First In, First Out (FIFO): Arrange items in your pantry and refrigerator based on their expiration dates. This ensures that older products are used before newer ones.\n" +
                        "Composting: Set up a compost system for food scraps that can't be used. Composting not only reduces waste but also enriches the soil.\n" +
                        "By incorporating these practices, you contribute to a more sustainable and eco-friendly kitchen, making a positive impact on both your household and the environment."
        ));

        return educationItems;
    }
}