package EducationModule;

public class EducationItem {

    String imagePath;

    String eduTitle;

    String eduDesc;

    String eduContents;

    public EducationItem(String imagePath, String eduTitle, String eduDesc, String eduContents) {
        this.imagePath = imagePath;
        this.eduTitle = eduTitle;
        this.eduDesc = eduDesc;
        this.eduContents = eduContents;
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

    public String getEduContents() {
        return eduContents;
    }
}
