package com.techwizards.wia2007_trash2treasure;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReportItem {
    UUID id = UUID.randomUUID();
    private String reportTitle;
    private String reportType;
    private String reportDescription;
    private String address;
    private String reporterName;
    private String status;
    private String reportDate;
    private String reportTime;

    public ReportItem() {}

    public ReportItem(String reportTitle, String reportType, String reportDescription, String address, String reporterName, String status, String reportDate, String reportTime) {
        this.reportTitle = reportTitle;
        this.reportType = reportType;
        this.reportDescription = reportDescription;
        this.address = address;
        this.reporterName = reporterName;
        this.status = status;
        this.reportDate = reportDate;
        this.reportTime = reportTime;
    }

    public Map<String, Object> toMap() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        System.out.println(json);
        Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public String getReportType() {
        return reportType;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public String getAddress() {
        return address;
    }

    public String getReporterName() {
        return reporterName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReportDate() {
        return reportDate;
    }

    public String getReportTime() {
        return reportTime;
    }
}
