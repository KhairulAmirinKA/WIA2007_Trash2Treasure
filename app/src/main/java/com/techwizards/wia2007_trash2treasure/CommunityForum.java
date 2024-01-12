package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.List;

public class CommunityForum extends Fragment implements CommunityForumAdapter.OnJoinClickListener{

    RecyclerView recyclerView;
    CommunityForumAdapter adapter;

    public CommunityForum() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community_forum, container, false);

        MaterialIconView btnDismiss = view.findViewById(R.id.BtnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        recyclerView = view.findViewById(R.id.RVCommunityForum);
        adapter = new CommunityForumAdapter(getList());

        // Set the join click listener in the CommunityForum fragment
        adapter.setOnJoinClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    public List<CommunityForumItem> getList() {
        List<CommunityForumItem> list = new ArrayList<>();

        list.add(new CommunityForumItem("https://www.gading.com.my/v2/wp-content/uploads/2021/04/Euresi-Waste-Management-Your-Partner-in-Plastics-bales-of-plastic-740x328-1.png", "Problem of Waste", "Discuss about how waste problems will affect our life.", 13));
        list.add(new CommunityForumItem("https://img.etimg.com/thumb/msid-101634906,width-480,height-360,imgsize-83338,resizemode-75/the-anthropocene.jpg", "GreenTech Enthusiasts", "Discuss about latest technologies in waste management.", 21));
        list.add(new CommunityForumItem("https://waste4change.com/blog/wp-content/uploads/86d36-single-use-plastic-1.jpeg", "Plastic-Free Pioneers", "Discuss strategies to reduce single-use plastics.", 8));

        return list;
    }

    private void replaceFragment(Fragment fragment) {
        Navigation.findNavController(requireView()).navigate(R.id.action_DestJoinCommunity_to_communityForumChatting /* pass any bundle if needed */);
    }
    @Override
    public void onJoinClick(int position) {
        replaceFragment(CommunityForumChatting.newInstance(String.valueOf(position)));
    }
}