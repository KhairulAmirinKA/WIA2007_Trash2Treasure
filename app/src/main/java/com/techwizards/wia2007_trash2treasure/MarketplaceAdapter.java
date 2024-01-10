package com.techwizards.wia2007_trash2treasure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
public class MarketplaceAdapter extends RecyclerView.Adapter<MarketplaceAdapter.ViewHolder>  {

    List<MarketItem> marketItemList;

    public MarketplaceAdapter(List<MarketItem> marketItemList) {
        this.marketItemList = marketItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketplace_item_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MarketItem item = marketItemList.get(position);

        Picasso.get().load(item.getImage()).into(holder.productImage);
        holder.productName.setText(item.getName());
        holder.productDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return marketItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.IVMarketplaceImage);
            productName = itemView.findViewById(R.id.TVMarketplaceName);
            productDescription = itemView.findViewById(R.id.TVMarketplaceDescription);
        }
    }
}