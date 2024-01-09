package com.techwizards.wia2007_trash2treasure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycledItemPickupListAdapter extends RecyclerView.Adapter<RecycledItemPickupListAdapter.ViewHolder> {

    private List<String> types;

    public RecycledItemPickupListAdapter(List<String> types) {
        this.types = types;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String type = types.get(position);
        holder.typeTextView.setText(type);
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.TVTextItem);
        }
    }
}