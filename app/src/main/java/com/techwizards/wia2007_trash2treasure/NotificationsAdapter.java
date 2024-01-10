package com.techwizards.wia2007_trash2treasure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.Date;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private List<NotificationsItem> notificationsItems;
    private OnItemClickListener clickListener;

    public NotificationsAdapter(List<NotificationsItem> notificationsItems) {
        this.notificationsItems = notificationsItems;
    }

    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_item_row_view, parent, false);
        return new NotificationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int position) {
        NotificationsItem item = notificationsItems.get(position);

        if (item.isRead()) {
            holder.isRead.setColorResource(R.color.gray);
        } else {
            holder.isRead.setColorResource(R.color.red);
        }
        holder.textNotificationTitle.setText(item.getType());
        holder.textNotificationTime.setText(item.getDate());
        holder.textNotificationBody.setText(item.getBody());

        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.onItemClick(position);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    public int getItemCount() {
        return notificationsItems.size();
    }

    public NotificationsItem getItem(int position) {
        return notificationsItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialIconView isRead;
        TextView textNotificationTitle;
        TextView textNotificationTime;
        TextView textNotificationBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            isRead = itemView.findViewById(R.id.IsReadDot);
            textNotificationTitle = itemView.findViewById(R.id.TVNotificationTitle);
            textNotificationTime = itemView.findViewById(R.id.TVNotificationTime);
            textNotificationBody = itemView.findViewById(R.id.TVNotificationBody);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(position);
                }
            }
        }
    }

    public void updateList(List<NotificationsItem> newList) {
        notificationsItems.clear();
        notificationsItems.addAll(newList);
        notifyDataSetChanged();
    }
}
