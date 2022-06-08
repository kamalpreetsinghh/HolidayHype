package com.gbc.holidayhype.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gbc.holidayhype.models.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UserRepository {
    private final String COLLECTION_NAME = "users";
    private final String TAG = this.getClass().getCanonicalName();

    private final FirebaseFirestore db;
    private final CollectionReference collection;
    public MutableLiveData<User> user = new MutableLiveData<>();

    public UserRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.collection = this.db.collection(this.COLLECTION_NAME);
    }

    public void getUserDetails(String userID) {
        DocumentReference document = this.collection.document(userID);
        document.addSnapshotListener((snapshot, error) -> {
            if (error != null) {
                Log.e(TAG, "getUserDetails: ", error);
                return;
            }
            if (snapshot != null && snapshot.exists()) {
                Map<String, Object> data = snapshot.getData();
                User userDetails = snapshot.toObject(User.class);
                user.postValue(userDetails);
                Log.d(TAG, "Current data: " + snapshot.getData());
            } else {
                Log.d(TAG, "Current data: null");
            }
        });
    }

    public void addUserDetails(User user) {
        this.collection.document(user.getUserID()).set(user);
    }

}
