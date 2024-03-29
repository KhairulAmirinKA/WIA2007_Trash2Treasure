package com.techwizards.wia2007_trash2treasure;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataManager {

    //constant for sharedPreferences
    private static final String PREF_NAME = "DataManagerPrefs";
    private static final String KEY_CURRENT_USER = "current_user";

    //instance to interact with FireStore
    private final FirebaseService firebaseService = new FirebaseService();

    private static DataManager instance;

    //objects to manage data
    public CurrentUser currentUser;
    public List<ProfileItem> profileItems = new ArrayList<>(); //list of profile items
    public List<ReportItem> reportItems = new ArrayList<>(); //list of report items
    public List<MarketItem> productItems = new ArrayList<>(); //list of advertised products
    public List<CommunityForumItem> communityForumItems = new ArrayList<>(); //list of community forums
    public List<VolunteerItem> volunteerItems = new ArrayList<>();

    //fetch from firebase
    public void fetchProfile() {
        firebaseService.fetchUserProfiles(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                    //convert fetched document to ProfileItem
                    ProfileItem profileItem = documentSnapshot.toObject(ProfileItem.class);

                    //add to list
                    profileItems.add(profileItem);
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

    public void saveUser() {
        for (ProfileItem item : profileItems) {
            updateProfile(item);
        }
    }

    //add new profile
    public void addProfile(ProfileItem newProfile) {
        //add profile item to local list
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

    //update current user profile
    public void updateProfile(ProfileItem updatedProfile) {
        int index = -1;
        for (int i = 0; i < profileItems.size(); i++) {
            if (profileItems.get(i).getEmail().equals(updatedProfile.getEmail())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            profileItems.set(index, updatedProfile);
            currentUser.clearCurrentUser();
            currentUser.setCurrentUser(updatedProfile);

            firebaseService.saveUserProfile(updatedProfile, task -> {
                if (task.isSuccessful()) {
                    System.out.println("FirebaseService: Update User Success!");
                } else {
                    Exception exception = task.getException();
                    if (exception != null) {
                        exception.printStackTrace();
                    }
                }
            });
        }
    }

    //fetch the report from Firebase
    public void fetchReport() {
        firebaseService.fetchReports(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    ReportItem reportItem = documentSnapshot.toObject(ReportItem.class);
                    reportItems.add(reportItem);
                }
                System.out.println("FirebaseService: Fetch Reports Success!");
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
    }

    //add new report to the firebase
    public void addNewReport(ReportItem reportItem) {

        //add report item to local list
        reportItems.add(reportItem);
        firebaseService.addNewReport(reportItem, task -> {
            if (task.isSuccessful()) {
                System.out.println("FirebaseService: Add New Report Success!");

            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void addReportImage(Uri imageUri, ImageUploadCallback callback) {
        firebaseService.addReportImage(imageUri, uri -> {
            callback.onUploadSuccess(uri.toString());
        });
    }

    public void fetchProduct() {
        firebaseService.fetchProducts(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    MarketItem item = documentSnapshot.toObject(MarketItem.class);
                    productItems.add(item);
                }
                System.out.println("FirebaseService: Fetch Reports Success!");
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void addNewProduct(MarketItem item) {
        //add product item to local list
        productItems.add(item);
        firebaseService.addNewProduct(item, task -> {
            if (task.isSuccessful()) {
                System.out.println("FirebaseService: Add New Advertise Product Success!");
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void addProductImage(Uri imageUri, ImageUploadCallback callback) {
        firebaseService.addProductImage(imageUri, uri -> {
            callback.onUploadSuccess(uri.toString());
        });
    }

    public void addNewVolunteer(VolunteerItem volunteerItem){
        //add to local list
        volunteerItems.add(volunteerItem);

        firebaseService.addNewVolunteer(volunteerItem, task -> {
            if (task.isSuccessful()){
                System.out.println("Firebase service: add new volunteer successful");
            }
            else {
                System.out.println("gagal");
            }
        });
    }

    public void fetchVolunteer(){
        firebaseService.fetchVolunteer(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                    String dbID = documentSnapshot.getId();

                    UUID uuid;
                    try {
                        uuid = UUID.fromString(dbID);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        uuid = null;
                    }

                    VolunteerItem item = documentSnapshot.toObject(VolunteerItem.class);
                    item.id = uuid;
                    volunteerItems.add(item);
                }
                System.out.println("Firebase service: fetch volunteer success");
            }
            else {
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
    }

    //fetch forum from firebase
    public void fetchForum() {
        firebaseService.fetchForums(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    String dbID = documentSnapshot.getId();

                    UUID uuid;
                    try {
                        uuid = UUID.fromString(dbID);
                    } catch (IllegalArgumentException e) {
                        uuid = null;
                    }

                    //convert fetched document to ProfileItem
                    CommunityForumItem item = documentSnapshot.toObject(CommunityForumItem.class);
                    item.id = uuid;
                    System.out.println("UUID" + item.id.toString() + " : " + uuid.toString());

                    //add to list
                    communityForumItems.add(item);
                }
                System.out.println("FirebaseService: Fetch Forum Success!");
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
    }

    //add new forum
    public void addForum(CommunityForumItem forumItem) {
        //add forum item to local list
        communityForumItems.add(forumItem);
        firebaseService.saveForum(forumItem, task -> {
            if (task.isSuccessful()) {
                System.out.println("FirebaseService: Save Forum Success!");
            } else {
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
    }

    //update forum
    public void updateForum(CommunityForumItem forumItem) {
        int index = -1;
        for (int i = 0; i < communityForumItems.size(); i++) {
            if (communityForumItems.get(i).id.equals(forumItem.id)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            communityForumItems.set(index, forumItem);

            firebaseService.saveForum(forumItem, task -> {
                if (task.isSuccessful()) {
                    System.out.println("FirebaseService: Update Forum Success!");
                } else {
                    Exception exception = task.getException();
                    if (exception != null) {
                        exception.printStackTrace();
                    }
                }
            });
        }
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public DataManager() { //methods to call when instance of DataManager is created
        fetchProfile(); // user profile
        fetchReport(); // report
        fetchProduct(); // product
        fetchForum(); // forum
        fetchVolunteer(); // volunteer
    }

    //load data from SharedPreferences
    public void load(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String currentUserJson = preferences.getString(KEY_CURRENT_USER, "");
        if(!currentUserJson.isEmpty()) {
            //deserialize Gson to currentUser
            currentUser = gson.fromJson(currentUserJson, CurrentUser.class);
        }
    }

    //save data to SharedPreferences
    public void save(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        //serialize CurrentUser to JSON
        String currentUserJson = gson.toJson(currentUser);

        //save serialized CurrentUser to SharedPreferences
        editor.putString(KEY_CURRENT_USER, currentUserJson);

        //save each profile item to Firebase
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

        editor.apply(); //apply changes to SharedPreferences
    }

    public interface ImageUploadCallback {
        void onUploadSuccess(String imageUri);
        void unUploadFailure(Exception e);
    }
}

