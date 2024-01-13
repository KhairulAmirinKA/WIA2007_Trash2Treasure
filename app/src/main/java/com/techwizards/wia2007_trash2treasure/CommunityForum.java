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

public class CommunityForum extends Fragment implements CommunityForumAdapter.OnJoinClickListener{

    RecyclerView recyclerView;
    CommunityForumAdapter adapter;
    DataManager dataManager = DataManager.getInstance();
    private static CommunityForum instance;

    public CommunityForum() {}

    public static CommunityForum getInstance() {
        if (instance == null) {
            instance = new CommunityForum();
        }
        return instance;
    }
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
        return dataManager.communityForumItems;
    }

    private void replaceFragment(Fragment fragment) {
        Navigation.findNavController(requireView()).navigate(R.id.action_DestJoinCommunity_to_communityForumChatting /* pass any bundle if needed */);
    }
    @Override
    public void onJoinClick(int position) {
        replaceFragment(CommunityForumChat.newInstance(String.valueOf(position)));
    }
}