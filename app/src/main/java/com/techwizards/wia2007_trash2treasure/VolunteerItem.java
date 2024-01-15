package com.techwizards.wia2007_trash2treasure;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VolunteerItem implements Serializable {

    UUID id = UUID.randomUUID();
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

    boolean isEnrolled ;

    public VolunteerItem() {} //to prevent error fetching data from firebase

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

    public Map<String, Object> toMap(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        System.out.println(json);

        Type type=  new TypeToken<HashMap<String, Object>>(){}.getType();
        return gson.fromJson(json, type);
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

    public boolean isEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(boolean enrolled) {
        isEnrolled = enrolled;
    }
}

