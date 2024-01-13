package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommunityForumChat extends Fragment {
    private List<Chat> chats;
    private CommunityForumChatAdapter chatAdapter;
    private EditText chatInputField;
    private String communityForumName;

    DataManager dataManager = DataManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community_forum_chatting, container, false);
        //return inflater.inflate(R.layout.fragment_community_forum_chatting, container, false);

        // Set up the BtnBack click listener
        view.findViewById(R.id.BtnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous fragment or pop the fragment from the back stack
                Navigation.findNavController(view).navigateUp();
            }
        });

        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
        CommunityForumItem forumItem = CommunityForum.getInstance().getList().get(position);

        TextView TVName = view.findViewById(R.id.TVCommunityForumChatName);
        TVName.setText(forumItem.getForumName());

        // Initialize RecyclerView and adapter
        RecyclerView recyclerView = view.findViewById(R.id.chat_recycler_view);
        chats = forumItem.getChats(); // Initialize with your data
        chatAdapter = new CommunityForumChatAdapter(chats);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(chatAdapter);

        chatInputField = view.findViewById(R.id.ETCommunityForumNewChat);

        view.findViewById(R.id.BtnCommunitySendChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                // Get the typed message from the input field
                String messageText = chatInputField.getText().toString();

                // Check if the message is not empty
                if (!TextUtils.isEmpty(messageText)) {
                    // Add the message to the chatMessages list
                    chats.add(new Chat(dataManager.currentUser.getCurrentUser().getName(), messageText, time.format(new Date())));

                    dataManager.updateForum(forumItem);
                    // Notify the adapter that the dataset has changed
                    chatAdapter.notifyDataSetChanged();

                    // Clear the input field after sending the message
                    chatInputField.setText("");
                }
            }
        });

        return view;
    }

    public static CommunityForumChat newInstance(String forumItemId) {
        CommunityForumChat fragment = new CommunityForumChat();
        Bundle args = new Bundle();
        args.putString("forumItemId", forumItemId);
        fragment.setArguments(args);
        return fragment;
    }
}
