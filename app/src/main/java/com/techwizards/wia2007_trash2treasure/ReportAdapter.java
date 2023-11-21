package com.techwizards.wia2007_trash2treasure;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<ReportItem> reportList;

    public ReportAdapter(List<ReportItem> reportList) {
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportItem item = reportList.get(position);

        holder.textReportTitle.setText(item.getReportTitle());
        holder.textReportType.setText(item.getReportType());
        holder.textAddress.setText(item.getAddress());
        holder.textReporterName.setText(item.getReporterName());
        holder.textStatus.setText(item.getStatus());
        holder.textReportDate.setText(item.getReportDate());
        holder.textReportTime.setText(item.getReportTime());
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textReportTitle;
        TextView textReportType;
        TextView textAddress;
        TextView textReporterName;
        TextView textStatus;
        TextView textReportDate;
        TextView textReportTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textReportTitle = itemView.findViewById(R.id.textReportTitle);
            textReportType = itemView.findViewById(R.id.textReportType);
            textAddress = itemView.findViewById(R.id.textAddress);
            textReporterName = itemView.findViewById(R.id.textReporterName);
            textStatus = itemView.findViewById(R.id.textStatus);
            textReportDate = itemView.findViewById(R.id.textReportDate);
            textReportTime = itemView.findViewById(R.id.textReportTime);
        }
    }

    public void updateList(List<ReportItem> newList) {
        reportList.clear();
        reportList.addAll(newList);
        notifyDataSetChanged();
    }
}
