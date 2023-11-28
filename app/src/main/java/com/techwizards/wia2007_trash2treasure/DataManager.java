package com.techwizards.wia2007_trash2treasure;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String PREF_NAME = "DataManagerPrefs";
    private static final String KEY_CURRENT_USER = "current_user";
    private static final String KEY_PROFILE_ITEMS = "profile_items";

    private static DataManager instance;
    public CurrentUser currentUser;
    public List<ProfileItem> profileItems = new ArrayList<>();

    public void fetchProfile() {
        profileItems.add(ProfileItem.testData());
    }

    public void addProfile(ProfileItem newProfile) {
        profileItems.add(newProfile);
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public DataManager() {
        fetchProfile();
    }

    public void load(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String currentUserJson = preferences.getString(KEY_CURRENT_USER, "");
        if(!currentUserJson.isEmpty()) {
            currentUser = gson.fromJson(currentUserJson, CurrentUser.class);
        }

        String profileItemJson = preferences.getString(KEY_PROFILE_ITEMS, "");
        if (!profileItemJson.isEmpty()) {
            Type listType = new TypeToken<List<ProfileItem>>() {}.getType();
            profileItems = gson.fromJson(profileItemJson, listType);
        }
    }

    public void save(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        String currentUserJson = gson.toJson(currentUser);
        editor.putString(KEY_CURRENT_USER, currentUserJson);

        String profileItemsJson = gson.toJson(profileItems);
        editor.putString(KEY_PROFILE_ITEMS, profileItemsJson);

        editor.apply();
    }
}
