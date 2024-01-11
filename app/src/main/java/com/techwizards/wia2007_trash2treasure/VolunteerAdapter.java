package com.techwizards.wia2007_trash2treasure;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.ViewHolder> {

    private final List<VolunteerItem> volunteerItems;

    public VolunteerAdapter(List<VolunteerItem> volunteerItems) {
        this.volunteerItems = volunteerItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteer_item_row_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VolunteerItem item = volunteerItems.get(position);

        Picasso.get().load(item.getImagePath()).error(R.drawable.ic_launcher_foreground).into(holder.volunteerImage);
        holder.textVolunteerTitle.setText(item.volunteerTitle);
        holder.textVolunteerDesc.setText(item.volunteerDesc);
        holder.textVolunteerPoints.setText(item.volunteerPoints + "\npoints");
    }

    @Override
    public int getItemCount() {
        return volunteerItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView volunteerImage;
        TextView textVolunteerTitle;
        TextView textVolunteerDesc;
        TextView textVolunteerPoints;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            volunteerImage = itemView.findViewById(R.id.VolunteerPostImage);
            textVolunteerTitle = itemView.findViewById(R.id.VolunteerTitle);
            textVolunteerDesc = itemView.findViewById(R.id.VolunteerDesc);
            textVolunteerPoints = itemView.findViewById(R.id.VolunteerPoints);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<VolunteerItem> newList) {
        volunteerItems.clear();
        volunteerItems.addAll(newList);
        notifyDataSetChanged();
    }
}