package com.techwizards.wia2007_trash2treasure;

public class EducationItem {

    String imagePath;

    String eduTitle;

    String eduDesc;

    public EducationItem(String imagePath, String eduTitle, String eduDesc) {
        this.imagePath = imagePath;
        this.eduTitle = eduTitle;
        this.eduDesc = eduDesc;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getEduTitle() {
        return eduTitle;
    }

    public String getEduDesc() {
        return eduDesc;
    }
}
