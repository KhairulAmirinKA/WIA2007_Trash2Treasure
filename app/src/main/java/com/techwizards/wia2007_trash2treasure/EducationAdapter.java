package com.techwizards.wia2007_trash2treasure;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {

    public static final String POSITION = "POSITION";
    private final List<EducationItem> educationItems ;

    public EducationAdapter(List<EducationItem> educationItems) {
        this.educationItems = educationItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.edu_item_row_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EducationItem item = educationItems.get(position);

        Picasso.get().load(item.getImagePath()).error(R.drawable.ic_launcher_foreground).into(holder.EduPostImage);

        holder.EduTitle.setText( item.eduTitle);
        holder.EduDesc.setText(item.eduDesc);
        
        holder.BtnEduReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Let's learn about "+item.getEduTitle(), Toast.LENGTH_SHORT).show();

                //use Bundle tp send data across fragment
                Bundle bundle = new Bundle();

                //utk tahu position dlm List
                bundle.putInt(POSITION, holder.getAdapterPosition() );

                //go to the fragment containing the articles of a topic.
                //send data using Bundle to EducationArticles
                Navigation.findNavController(view).navigate(R.id.DestEduArticles, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return educationItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView EduPostImage;
        TextView EduTitle;
        TextView EduDesc;
        
        Button BtnEduReadMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            EduPostImage= itemView.findViewById(R.id.EduPostImage);

            EduTitle= itemView.findViewById(R.id.EduTitle);
            EduDesc= itemView.findViewById(R.id.EduDesc);

            BtnEduReadMore= itemView.findViewById(R.id.BtnEduReadMore);

        }
    } //viewholder



    @SuppressLint("Notify Data change")
    public void updateList(List<EducationItem> newList){

        educationItems.clear();
        educationItems.addAll( newList);

        notifyDataSetChanged();
    }



}
