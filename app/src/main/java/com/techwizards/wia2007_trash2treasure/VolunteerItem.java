package com.techwizards.wia2007_trash2treasure;

import java.io.Serializable;

public class VolunteerItem implements Serializable {
    String ImagePath;
    String volunteerTitle;
    String volunteerDesc;
    int volunteerPoints;

    String volunteerVenue;
    String volunteerStartDate;
    String volunteerEndDate;
    String volunteerTime;
    int volunteerParticipantCount;
    String volunterCategories;

    public VolunteerItem(String imagePath, String volunteerTitle, String volunteerDesc, int volunteerPoints, String volunteerStartDate, String volunteerEndDate, String volunteerTime, int volunteerParticipantCount, String volunterCategories) {
        ImagePath = imagePath;
        this.volunteerTitle = volunteerTitle;
        this.volunteerDesc = volunteerDesc;
        this.volunteerPoints = volunteerPoints;
        this.volunteerStartDate = volunteerStartDate;
        this.volunteerEndDate = volunteerEndDate;
        this.volunteerTime = volunteerTime;
        this.volunteerParticipantCount = volunteerParticipantCount;
        this.volunterCategories = volunterCategories;
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

    public String getVolunterCategories() {
        return volunterCategories;
    }
}

