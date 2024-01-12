package com.techwizards.wia2007_trash2treasure;

public class CommunityForumItem {
    String forumImage;
    String forumName;
    String forumDescription;
    int forumParticipants;
    private String id;

    public CommunityForumItem(String forumImage, String forumName, String forumDescription, int forumParticipants) {
        this.forumImage = forumImage;
        this.forumName = forumName;
        this.forumDescription = forumDescription;
        this.forumParticipants = forumParticipants;
    }

    public String getForumImage() {
        return forumImage;
    }

    public String getForumName() {
        return forumName;
    }

    public String getForumDescription() {
        return forumDescription;
    }

    public int getForumParticipants() {
        return forumParticipants;
    }
    public String getId() {
        return id;
    }
}
