package com.techwizards.wia2007_trash2treasure;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseService {
    private static final String PROFILES_COLLECTION = "user_profiles";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference profileCollection = db.collection((PROFILES_COLLECTION));

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
}
