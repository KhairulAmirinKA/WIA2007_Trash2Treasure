package com.techwizards.wia2007_trash2treasure;

public class ReportItem {
    private String reportTitle;
    private String reportType;
    private String address;
    private String reporterName;
    private String status;
    private String reportDate;
    private String reportTime;

    public ReportItem(String reportTitle, String reportType, String address, String reporterName, String status, String reportDate, String reportTime) {
        this.reportTitle = reportTitle;
        this.reportType = reportType;
        this.address = address;
        this.reporterName = reporterName;
        this.status = status;
        this.reportDate = reportDate;
        this.reportTime = reportTime;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
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

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
}
