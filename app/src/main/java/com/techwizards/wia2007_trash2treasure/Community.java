package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Community extends Fragment {

    RecyclerView productRecycler;
    MarketplaceAdapter marketplaceAdapter;

    RecyclerView forumRecycler;
    CommunityForumAdapterSmall forumAdapter;

    public Community() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        productRecycler = view.findViewById(R.id.RVCommunityProducts);
        marketplaceAdapter = new MarketplaceAdapter(new Marketplace().getProducts());
        productRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        productRecycler.setAdapter(marketplaceAdapter);

        TextView btnMoreProducts = view.findViewById(R.id.BtnMoreProducts);
        btnMoreProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestMarketplace);
            }
        });

        forumRecycler = view.findViewById(R.id.RVCommunityForum);
        forumAdapter = new CommunityForumAdapterSmall(CommunityForum.getInstance().getList());
        forumRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        forumRecycler.setAdapter(forumAdapter);

        forumAdapter.setOnItemClickListener(new CommunityForumAdapterSmall.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click, navigate to CommunityForumChatFragment
                CommunityForumItem forumItem = forumAdapter.communityForumItems.get(position);

                Bundle args = new Bundle();
                args.putInt("position", position);
                Navigation.findNavController(view).navigate(R.id.action_DestCommunity_to_communityForumChatting, args);
            }
        });

        Button btnJoinCommunity = view.findViewById(R.id.BtnCommunityJoinCommunity);
        btnJoinCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestJoinCommunity);
            }
        });

        //edu resources
        Button BtnCommunityEduResources= view.findViewById(R.id.BtnCommunityEduResources);
        BtnCommunityEduResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.DestEduResources);
            }
        });

        return view;
    }
}
