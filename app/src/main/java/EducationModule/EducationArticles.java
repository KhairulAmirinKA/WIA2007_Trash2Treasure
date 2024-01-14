package EducationModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.techwizards.wia2007_trash2treasure.R;

import net.steamcrafted.materialiconlib.MaterialIconView;


public class EducationArticles extends Fragment {
    public static final String POSITION = "POSITION";

    MaterialIconView BtnDismiss;
    TextView TVEduArticleTitle;
    ImageView IVEduArticle;
    TextView TVEduArticleContents;
    ImageView IVLike;
    ImageView IVDislike;

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

        //init UI
        initView(view);

        //back btn
        BtnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        // Retrieve data from the arguments bundle. we have list of EducationResources. it has position
        int position=0;

        //get data from EducationAdapter
        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(POSITION)) {
             position = bundle.getInt(POSITION, 0);
            // Now you have the data in the receivedData variable
            // You can use it as needed
        }


        EducationResources educationResources = new EducationResources(); //instantiate fragment
        //this fragment ada method utk create list of EducationItems
        EducationItem currentItem= educationResources.generateEduList().get(position); //current EducationItem

        //set title
        TVEduArticleTitle.setText( currentItem.getEduTitle() );

        //set image from remote
        Glide.with(view).load( currentItem.getImagePath()).into(IVEduArticle);

        //set the content
        TVEduArticleContents.setText( currentItem.getEduContents() );


        //click the like icon
        IVLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Thanks for your feedback", Toast.LENGTH_SHORT).show();
            }
        });

        //click dislike icon
        IVDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "We will review this article again", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    //init UI
    private void initView(View view) {
        BtnDismiss= view.findViewById(R.id.BtnDismiss);

        TVEduArticleTitle= view.findViewById(R.id.TVEduArticleTitle);

        IVEduArticle= view.findViewById(R.id.IVEduArticle);

        TVEduArticleContents= view.findViewById(R.id.TVEduArticleContents);

        IVLike= view.findViewById(R.id.IVLike);
        IVDislike= view.findViewById(R.id.IVDislike);
    }
}