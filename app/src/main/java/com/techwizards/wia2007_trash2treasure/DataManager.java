package com.techwizards.wia2007_trash2treasure;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String PREF_NAME = "DataManagerPrefs";
    private static final String KEY_CURRENT_USER = "current_user";

    private final FirebaseService firebaseService = new FirebaseService();

    private static DataManager instance;
    public CurrentUser currentUser;
    public List<ProfileItem> profileItems = new ArrayList<>();

    public void fetchProfile() {
        firebaseService.fetchUserProfiles(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    ProfileItem profileItem = documentSnapshot.toObject(ProfileItem.class);
                    profileItems.add(profileItem);
                    System.out.println(profileItem.getPassword());
                }
                System.out.println("FirebaseService: Fetch User Success!");
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void addProfile(ProfileItem newProfile) {
        profileItems.add(newProfile);
        firebaseService.saveUserProfile(newProfile, task -> {
            if (task.isSuccessful()) {
                System.out.println("FirebaseService: Save New User Success!");
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
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
    }

    public void save(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        String currentUserJson = gson.toJson(currentUser);
        editor.putString(KEY_CURRENT_USER, currentUserJson);

        for (ProfileItem item : profileItems) {
            firebaseService.saveUserProfile(item, task -> {
                if (task.isSuccessful()) {
                    System.out.println("FirebaseService: Save New User Success!");
                } else {
                    Exception exception = task.getException();
                    if (exception != null) {
                        exception.printStackTrace();
                    }
                }
            });
        }

        editor.apply();
    }
}

