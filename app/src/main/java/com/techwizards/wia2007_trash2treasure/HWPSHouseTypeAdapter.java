package com.techwizards.wia2007_trash2treasure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HWPSHouseTypeAdapter extends RecyclerView.Adapter<HWPSHouseTypeAdapter.HouseTypeViewHolder> {
    private List<HWPSItem> hwpsItemList;

    public HWPSHouseTypeAdapter(List<HWPSItem> hwpsItemList) {
        this.hwpsItemList = hwpsItemList;
    }

    @NonNull
    @Override
    public HouseTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.household_type_row_view, parent, false);
        return new HouseTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseTypeViewHolder holder, int position) {
        HWPSItem hwpsItem = hwpsItemList.get(position);

        holder.bind(hwpsItem);
    }

    @Override
    public int getItemCount() {
        return hwpsItemList.size();
    }

    public class HouseTypeViewHolder extends RecyclerView.ViewHolder {
        private TextView houseType;
        private RecyclerView collectionType;

        public HouseTypeViewHolder(@NonNull View itemView) {
            super(itemView);

            houseType = itemView.findViewById(R.id.TVHWPSHouseType);
            collectionType = itemView.findViewById(R.id.RVHWPSCollectionType);
        }

        public void bind(HWPSItem hwpsItem) {
            houseType.setText(hwpsItem.getHouseType());

            HWPSCollectionItemAdapter inner = new HWPSCollectionItemAdapter(hwpsItem.getCollectionType());
            collectionType.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            collectionType.setAdapter(inner);
        }
    }
}
