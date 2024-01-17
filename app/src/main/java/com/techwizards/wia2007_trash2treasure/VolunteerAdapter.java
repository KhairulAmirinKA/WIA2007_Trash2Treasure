package com.techwizards.wia2007_trash2treasure;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.ViewHolder> {
    public static final String VOLUNTEER_ITEM_KEY = "VOLUNTEER_ITEM_KEY";
    private final List<VolunteerItem> volunteerItems;
    ProfileItem currentUser = DataManager.getInstance().currentUser.getCurrentUser();

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

        //current item
        VolunteerItem item = volunteerItems.get(position);

        Picasso.get().load(item.getImagePath()).error(R.drawable.ic_launcher_foreground).into(holder.volunteerImage);
        holder.textVolunteerTitle.setText(item.volunteerTitle);
        holder.textVolunteerDesc.setText(item.volunteerDesc);
        holder.textVolunteerPoints.setText(item.volunteerPoints + "\npoints");

        //want to send data to other fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable(VOLUNTEER_ITEM_KEY, item); //send objek terus

        //click read more
        holder.TVVolunteerReadMore.setOnClickListener(view -> {
            Toast.makeText(view.getContext(), item.volunteerTitle, Toast.LENGTH_SHORT).show();

            //go to volunteer program details
            Navigation.findNavController(view).navigate(R.id.DestVolunteerProgramDetails, bundle);

        });

        if (currentUser.getJoinedVolunteer().contains(item.id)) {
            holder.BtnVolunteerJoin.setText("Joined");
        }
        //click join btn
        holder.BtnVolunteerJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.DestVolunteerRegistration, bundle);
            }
        });
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
        TextView TVVolunteerReadMore;

        Button BtnVolunteerJoin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            volunteerImage = itemView.findViewById(R.id.VolunteerPostImage);
            textVolunteerTitle = itemView.findViewById(R.id.VolunteerTitle);
            textVolunteerDesc = itemView.findViewById(R.id.VolunteerDesc);
            TVVolunteerReadMore= itemView.findViewById(R.id.TVVolunteerReadMore);
            textVolunteerPoints = itemView.findViewById(R.id.VolunteerPoints);

            BtnVolunteerJoin = itemView.findViewById(R.id.BtnVolunteerJoin);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<VolunteerItem> newList) {
        volunteerItems.clear();
        volunteerItems.addAll(newList);
        notifyDataSetChanged();
    }
}