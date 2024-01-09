package com.techwizards.wia2007_trash2treasure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycledItemPickupAdapter extends RecyclerView.Adapter<RecycledItemPickupAdapter.ViewHolder> {

    List<RecyIPItem> recyIPItemList;

    public RecycledItemPickupAdapter(List<RecyIPItem> recyIPItemList) {
        this.recyIPItemList = recyIPItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycled_item_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyIPItem item = recyIPItemList.get(position);

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return recyIPItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayDate;
        TextView dayMonth;
        RecyclerView type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dayDate = itemView.findViewById(R.id.TVRecycleDayDate);
            dayMonth = itemView.findViewById(R.id.TVRecycleDayMonth);
            type = itemView.findViewById(R.id.RVRecycleItems);
        }

        public void bind(RecyIPItem item) {
            dayDate.setText(item.getDate().split(" ")[0]);
            dayMonth.setText(item.getDate().split(" ")[1]);

            List<String> items = item.getType();
            RecycledItemPickupListAdapter adapter = new RecycledItemPickupListAdapter(items);
            type.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            type.setAdapter(adapter);
        }
    }
}
