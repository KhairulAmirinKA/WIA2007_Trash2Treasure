package com.techwizards.wia2007_trash2treasure;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommunityForumItem {
    UUID id = UUID.randomUUID();
    String forumImage;
    String forumName;
    String forumDescription;
    int forumParticipants;
    List<Chat> chats;

    public CommunityForumItem() {}

    public CommunityForumItem(String forumImage, String forumName, String forumDescription, int forumParticipants) {
        this.forumImage = forumImage;
        this.forumName = forumName;
        this.forumDescription = forumDescription;
        this.forumParticipants = forumParticipants;
        this.chats = new ArrayList<>();
    }

    public Map<String, Object> toMap() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        System.out.println(json);
        Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
        return gson.fromJson(json, type);
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

    public List<Chat> getChats() {
        return chats;
    }
}
