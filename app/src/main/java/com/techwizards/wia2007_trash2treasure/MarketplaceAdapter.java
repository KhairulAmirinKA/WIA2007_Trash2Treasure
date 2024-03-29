package com.techwizards.wia2007_trash2treasure;

import android.content.Context;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
public class MarketplaceAdapter extends RecyclerView.Adapter<MarketplaceAdapter.ViewHolder>  {

    public static final String POSITION = "POSITION";
    List<MarketItem> marketItemList;

    public MarketplaceAdapter(List<MarketItem> marketItemList) {
        this.marketItemList = marketItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_product_item_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MarketItem item = marketItemList.get(position);

        Picasso.get().load(item.getImage()).error(R.drawable.ic_launcher_foreground).into(holder.productImage);
        holder.productName.setText(item.getName());
        holder.productDescription.setText(item.getDescription());
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //use bundle tp send data across fragment
                Bundle bundle = new Bundle();

                //utk tahu position dlm list
                bundle.putInt(POSITION, holder.getAdapterPosition());
                //go to the fragment containing the articles of a topic.
                //send data using Bundle to EducationArticles
                Navigation.findNavController(v).navigate(R.id.DestMarketItem, bundle);
            }
        });
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

            productImage = itemView.findViewById(R.id.IVCommunityProductImage);
            productName = itemView.findViewById(R.id.TVCommunityProductName);
            productDescription = itemView.findViewById(R.id.TVCommunityProductDesc);
        }
    }
}