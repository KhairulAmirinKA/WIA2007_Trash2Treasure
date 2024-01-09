package com.techwizards.wia2007_trash2treasure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;
import java.util.List;

public class HWPSCollectionItemAdapter extends RecyclerView.Adapter<HWPSCollectionItemAdapter.CollectionViewHolder> {

    private List<HWPSItem.CollectionType> collectionTypeList;

    public HWPSCollectionItemAdapter(List<HWPSItem.CollectionType> collectionTypeList) {
        this.collectionTypeList = collectionTypeList;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.household_item_row_view, parent, false);
        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
        HWPSItem.CollectionType collectionType = collectionTypeList.get(position);
        holder.bind(collectionType);
    }

    @Override
    public int getItemCount() {
        return collectionTypeList.size();
    }

    public class CollectionViewHolder extends RecyclerView.ViewHolder {
        private TextView collectionType;
        private TextView collectionDay;
        private TextView nextCollectionDate;
        private MaterialIconView icon;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);

            collectionType = itemView.findViewById(R.id.HWPSCollectionType);
            collectionDay = itemView.findViewById(R.id.HWPSCollectionDay);
            nextCollectionDate = itemView.findViewById(R.id.HWPSCollectionDate);
            icon = itemView.findViewById(R.id.HWPSCollectionIcon);
        }

        public void bind(HWPSItem.CollectionType collectionTypeItem) {
            collectionType.setText(collectionTypeItem.getType());
            collectionDay.setText(collectionTypeItem.getCollectionDay());
            nextCollectionDate.setText(collectionTypeItem.getNextCollectionDate());
            if (collectionTypeItem.getIcon().equals("bin")) {
                icon.setIcon(MaterialDrawableBuilder.IconValue.DELETE);
            } else if (collectionTypeItem.getIcon().equals("eco")) {
                icon.setIcon(MaterialDrawableBuilder.IconValue.LEAF);
            }
        }
    }
}
