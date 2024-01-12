package com.techwizards.wia2007_trash2treasure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CommunityForumAdapter extends RecyclerView.Adapter<CommunityForumAdapter.ViewHolder> {
    List<CommunityForumItem> communityForumItems;
    private OnJoinClickListener joinClickListener;

    public interface OnJoinClickListener {
        void onJoinClick(int position);
    }

    public void setOnJoinClickListener(OnJoinClickListener listener) {
        this.joinClickListener = listener;
    }

    public CommunityForumAdapter(List<CommunityForumItem> communityForumItems) {
        this.communityForumItems = communityForumItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_join_forum_item_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommunityForumItem item = communityForumItems.get(position);

        Picasso.get().load(item.getForumImage()).into(holder.image);
        holder.name.setText(item.getForumName());
        holder.description.setText(item.getForumDescription());

        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Joined", Toast.LENGTH_SHORT).show();
            }
        });

        holder.join.setOnClickListener(v -> {
            if (joinClickListener != null) {
                joinClickListener.onJoinClick(position);
            }
        });
    }

    static class ForumViewHolder extends RecyclerView.ViewHolder {
        Button joinButton;

        ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            joinButton = itemView.findViewById(R.id.BtnCommunityJoin);
            // ... other view bindings
        }
    }

    @Override
    public int getItemCount() {
        return communityForumItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView description;
        Button join;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.IVCommunityForumImage);
            name = itemView.findViewById(R.id.TVCommunityForumName);
            description = itemView.findViewById(R.id.TVCommunityForumDesc);
            join = itemView.findViewById(R.id.BtnCommunityJoin);
        }
    }
}
