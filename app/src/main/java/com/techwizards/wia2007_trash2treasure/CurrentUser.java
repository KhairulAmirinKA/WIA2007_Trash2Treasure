package com.techwizards.wia2007_trash2treasure;

public class CurrentUser {
    private ProfileItem currentUser;

    public ProfileItem getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(ProfileItem user) {
        currentUser = user;
    }

    public void clearCurrentUser() {
        currentUser = null;
    }
}

