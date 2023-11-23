package com.techwizards.wia2007_trash2treasure;

public class VolunteerItem {
    String ImagePath;
    String volunteerTitle;
    String volunteerDesc;
    int volunteerPoints;
    String volunteerStartDate;
    String volunteerEndDate;
    String volunteerTime;
    int volunteerParticipantCount;

    public VolunteerItem(String imagePath, String volunteerTitle, String volunteerDesc, int volunteerPoints, String volunteerStartDate, String volunteerEndDate, String volunteerTime, int volunteerParticipantCount) {
        ImagePath = imagePath;
        this.volunteerTitle = volunteerTitle;
        this.volunteerDesc = volunteerDesc;
        this.volunteerPoints = volunteerPoints;
        this.volunteerStartDate = volunteerStartDate;
        this.volunteerEndDate = volunteerEndDate;
        this.volunteerTime = volunteerTime;
        this.volunteerParticipantCount = volunteerParticipantCount;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getVolunteerStartDate() {
        return volunteerStartDate;
    }

    public String getVolunteerEndDate() {
        return volunteerEndDate;
    }

    public int getVolunteerParticipantCount() {
        return volunteerParticipantCount;
    }
}

