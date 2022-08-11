package com.gbc.holidayhype.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.gbc.holidayhype.model.BookingData;
import com.gbc.holidayhype.model.FlightsData;
import com.gbc.holidayhype.model.TicketData;
import com.gbc.holidayhype.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private final String COLLECTION_NAME = "users";
    private final String TAG = this.getClass().getCanonicalName();

    private final String FIELD_userID = "userID";
    private final String FIELD_firstName = "firstName";
    private final String FIELD_lastName = "lastName";
    private final String FIELD_email = "email";
    private final String FIELD_password= "password";

    public String loggedInUserEmail = "";


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

    public void updateUser(User user){

        Map<String, Object> data = new HashMap<>();
        data.put(FIELD_userID,user.getUserID());
        data.put(FIELD_firstName,user.getFirstName());
        data.put(FIELD_lastName,user.getLastName());
        data.put(FIELD_email,user.getEmail());
        data.put(FIELD_password,user.getPassword());

        try{
            db.collection(COLLECTION_NAME)
                    .document(user.getUserID())
                    .collection("userData")
                    .document(user.getUserID())
                    .update(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e(TAG,"onSuccess: Document successfully updated");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG,"onFailure: Unable to update the document"+ e.getLocalizedMessage());
                        }
                    });
        }
        catch (Exception ex){
            Log.e(TAG,"Errors: Exception occured" + ex.getLocalizedMessage());
        }
    }

    public void addUser(User user){
//        loggedInUserEmail = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.e("TAG", "addItem: --------"+user);
        try{
            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_userID,user.getUserID());
            data.put(FIELD_firstName,user.getFirstName());
            data.put(FIELD_lastName,user.getLastName());
            data.put(FIELD_email,user.getEmail());
            data.put(FIELD_password,user.getPassword());



            db.collection(COLLECTION_NAME)
                    .document(user.getUserID())
                    .collection("userData")
                    .document(user.getUserID())
                    .set(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }catch (Exception ex){
            Log.e(TAG,"addFriend"+ex.getLocalizedMessage());
        }
    }

    public void getAllUserData(){
        loggedInUserEmail = FirebaseAuth.getInstance().getCurrentUser().getUid();
        try{
            db.collection(COLLECTION_NAME)
                    .document(loggedInUserEmail)
                    .collection("userData")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                Log.e(TAG,"onEvent: Unable to get document changes" + error);
                                return;
                            }
                            User userdata = new User();
                            if (snapshot != null){
                                Log.e(TAG,"onEvent: Current changes" + snapshot.getDocumentChanges());

                                for(DocumentChange documentChange: snapshot.getDocumentChanges()){

                                    User currentUser = documentChange.getDocument().toObject(User.class);

//                                    currentFurniture.setId(documentChange.getDocument().getId());
                                    Log.e(TAG,"onEvent: Furniture Data: "+ currentUser.toString());
                                    switch (documentChange.getType()){
                                        case ADDED:

                                            userdata.setUserID(currentUser.getUserID());
                                            userdata.setFirstName(currentUser.getFirstName());
                                            userdata.setLastName(currentUser.getLastName());
                                            userdata.setEmail(currentUser.getEmail());
                                            userdata.setPassword(currentUser.getPassword());
                                            break;
                                        case MODIFIED:

                                            break;
                                        case REMOVED:
                                            break;
                                    }
                                }

                                user.postValue(userdata);

                            }else{
                                Log.e(TAG,"onEvent: No changes received");
                            }
                        }
                    });

        }
        catch (Exception ex){
            Log.e(TAG,"getA;;Friends: Exception occured"+ ex.getLocalizedMessage());
        }
    }
}
