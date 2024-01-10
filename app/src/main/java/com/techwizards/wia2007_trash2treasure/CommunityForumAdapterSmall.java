package com.techwizards.wia2007_trash2treasure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CommunityForumAdapterSmall extends RecyclerView.Adapter<CommunityForumAdapterSmall.ViewHolder> {
    List<CommunityForumItem> communityForumItems;

    public CommunityForumAdapterSmall(List<CommunityForumItem> communityForumItems) {
        this.communityForumItems = communityForumItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_forum_item_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommunityForumItem item = communityForumItems.get(position);

        Picasso.get().load(item.getForumImage()).into(holder.image);
        holder.name.setText(item.getForumName());

        String shortDesc = item.getForumDescription().substring(0, 30) + "...";
        holder.description.setText(shortDesc);

        String users = item.getForumParticipants() + " Users";
        holder.users.setText(users);
    }

    @Override
    public int getItemCount() {
        return communityForumItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView description;
        TextView users;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.IVCommunityForumImage);
            name = itemView.findViewById(R.id.TVCommunityForumName);
            description = itemView.findViewById(R.id.TVCommunityForumDesc);
            users = itemView.findViewById(R.id.TVCommunityForumUserCount);
        }
    }
}
