package com.techwizards.wia2007_trash2treasure;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.rpc.context.AttributeContext;

import java.util.List;

public class CommunityForumChatAdapter extends RecyclerView.Adapter<CommunityForumChatAdapter.ChatViewHolder> {

    DataManager dataManager = DataManager.getInstance();
    private List<Chat> chats;

    public CommunityForumChatAdapter(List<Chat> chats) {
        this.chats = chats;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_forum_chat_bubble_item_row_view, parent, false);
        return new ChatViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chats.get(position);

        String user = dataManager.currentUser.getCurrentUser().getName();
        if (chat.getUserName().equals(user)) {
            holder.layout.setGravity(Gravity.END);
        }
        holder.name.setText(chat.getUserName());
        holder.time.setText(chat.getTimestamp());
        holder.message.setText(chat.getMessageContent());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView name;
        TextView time;
        TextView message;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.LLChatLayout);
            name = itemView.findViewById(R.id.TVChatSender);
            time = itemView.findViewById(R.id.TVChatTime);
            message = itemView.findViewById(R.id.TVChatMessage);
        }
    }
}
