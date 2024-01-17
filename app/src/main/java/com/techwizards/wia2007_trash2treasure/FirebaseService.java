package com.techwizards.wia2007_trash2treasure;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class FirebaseService {
    private static final String PROFILES_COLLECTION = "user_profiles";
    private static final String REPORT_COLLECTION = "reports";
    private static final String PRODUCT_COLLECTION = "products";
    private static final String FORUM_COLLECTION = "forums";
    private static final String VOLUNTEER_COLLECTION = "volunteers";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private final CollectionReference profileCollection = db.collection(PROFILES_COLLECTION);
    private final CollectionReference reportCollection = db.collection(REPORT_COLLECTION);
    private final CollectionReference productCollection = db.collection(PRODUCT_COLLECTION);
    private final CollectionReference forumCollection = db.collection(FORUM_COLLECTION);
    private final CollectionReference volunteerCollection = db.collection(VOLUNTEER_COLLECTION);

    private static FirebaseService instance;

    public static synchronized FirebaseService getInstance() {
        if (instance == null) {
            instance = new FirebaseService();
        }
        return instance;
    }

    public FirebaseService() {}

    public void saveUserProfile(ProfileItem profileItem, OnCompleteListener<Void> onCompleteListener) {
        String email = profileItem.getEmail();
        if (email != null && !email.isEmpty()) {
            profileCollection.document(email)
                    .set(profileItem.toMap())
                    .addOnCompleteListener(onCompleteListener);
        }
    }

    public void fetchUserProfiles(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        profileCollection.get().addOnCompleteListener(onCompleteListener);
    }

    public void addNewReport(ReportItem reportItem, OnCompleteListener<Void> onCompleteListener) {
        String id = reportItem.id.toString();
        if (!id.isEmpty()) {
            reportCollection.document(id)
                    .set(reportItem.toMap())
                    .addOnCompleteListener(onCompleteListener);
        }
    }

    public void addReportImage(Uri imageUri, OnSuccessListener<Uri> onSuccessListener) {
        StorageReference imageRef = storageRef.child("reports/" + UUID.randomUUID().toString());

        imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(onSuccessListener);
        });
    }

    public void fetchReports(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        reportCollection.get().addOnCompleteListener(onCompleteListener);
    }

    public void addNewProduct(MarketItem item, OnCompleteListener<Void> onCompleteListener) {
        String id = item.uuid.toString();
        if (!id.isEmpty()) {
            productCollection.document(id)
                    .set(item.toMap())
                    .addOnCompleteListener(onCompleteListener);
        }
    }

    public void addProductImage(Uri imageUri, OnSuccessListener<Uri> onSuccessListener) {
        StorageReference imageRef = storageRef.child("products/" + UUID.randomUUID().toString());

        imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(onSuccessListener);
        });
    }

    public void fetchProducts(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        productCollection.get().addOnCompleteListener(onCompleteListener);
    }

    public void saveForum(CommunityForumItem item, OnCompleteListener<Void> onCompleteListener) {
        String id = item.id.toString();
        if (!id.isEmpty()) {
            forumCollection.document(id)
                    .set(item.toMap())
                    .addOnCompleteListener(onCompleteListener);
        }
    }

    public void fetchForums(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        forumCollection.get().addOnCompleteListener(onCompleteListener);
    }

    public void addNewVolunteer(VolunteerItem volunteerItem, OnCompleteListener<Void> onCompleteListener){
        String id= volunteerItem.id.toString();
        if(!id.isEmpty()){
            volunteerCollection.document(id)
                    .set(volunteerItem.toMap())
                    .addOnCompleteListener(onCompleteListener);
        }
    }

    public void fetchVolunteer(OnCompleteListener<QuerySnapshot> onCompleteListener){
        volunteerCollection.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateUserPoints(ProfileItem profileItem, int additionalPoints, OnCompleteListener<Void> onCompleteListener) {
        int currentPoints = profileItem.getPoints();
        int updatedPoints = currentPoints + additionalPoints;

        profileItem.setPoints(updatedPoints);

        String email = profileItem.getEmail();
        if (email != null && !email.isEmpty()) {
            profileCollection.document(email)
                    .set(profileItem.toMap())
                    .addOnCompleteListener(onCompleteListener);
        }
    }
}
