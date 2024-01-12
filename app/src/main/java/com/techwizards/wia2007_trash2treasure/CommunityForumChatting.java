package com.techwizards.wia2007_trash2treasure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommunityForumChatting extends Fragment {


    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private EditText chatInputField;
    private String communityForumName;


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

        chatInputField = view.findViewById(R.id.chat_input_field);
        view.findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the typed message from the input field
                String messageText = chatInputField.getText().toString();

                // Check if the message is not empty
                if (!TextUtils.isEmpty(messageText)) {
                    // Add the message to the chatMessages list
                    chatMessages.add(new ChatMessage("Ameera", messageText));

                    // Notify the adapter that the dataset has changed
                    chatAdapter.notifyDataSetChanged();

                    // Clear the input field after sending the message
                    chatInputField.setText("");
                }
            }
        });
        // Initialize RecyclerView and adapter
        RecyclerView recyclerView = view.findViewById(R.id.chat_recycler_view);
        chatMessages = new ArrayList<>(); // Initialize with your data
        chatAdapter = new ChatAdapter(chatMessages);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(chatAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve any passed data from arguments
        Bundle args = getArguments();
        if (args != null) {
            int communityId = args.getInt("communityId", -1);

        }
    }
    public static CommunityForumChatting newInstance(String forumItemId) {
        CommunityForumChatting fragment = new CommunityForumChatting();
        Bundle args = new Bundle();
        args.putString("forumItemId", forumItemId);
        fragment.setArguments(args);
        return fragment;
    }
}
