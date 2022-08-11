package com.gbc.holidayhype.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.gbc.holidayhype.model.BookingData;
import com.gbc.holidayhype.model.FlightsData;
import com.gbc.holidayhype.model.TicketData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Random;

public class FlightRepository {

        private final String Tag = this.getClass().getCanonicalName();
        private final FirebaseFirestore DB;
        private final String COLLECTION_Flight = "FlightsCollection";
        private final String COLLECTION_FLIGHTDATA = "Flights";
        private final String COLLECTION_DATA = "FlightData";
        private final String FIELD_Id = "id";
        private final String FIELD_flightName = "flightName";
        private final String FIELD_flightTo = "to";
        private final String FIELD_flightFrom = "from";
        private final String FIELD_departureTiming = "departureTiming";
        private final String FIELD_landingTiming = "landingTiming";
        private final String FIELD_totalTiming = "totalTiming";
        private final String FIELD_fare = "fare";
        private final String FIELD_type = "type";
        private final String FIELD_flightImage = "imageUrl";

        private final String FIELD_departureDate = "departureDate";
        private final String FIELD_landingDate= "landingDate";
        private final String FIELD_className= "className";
        private final String FIELD_numberOfChildren= "numberOfChildren";
        private final String FIELD_numberOfAdults= "numberOfAdults";


        public String loggedInUserEmail = "";

        public MutableLiveData<List<TicketData>> allFlights = new MutableLiveData<>();
        public MutableLiveData<TicketData> flightsFromDB = new MutableLiveData<>();

        public FlightRepository() {
            DB = FirebaseFirestore.getInstance();
        }



    public void addItem(ArrayList<FlightsData> newItem, BookingData bookingData){
        Random r = new Random();
        int low = 0;
        int high = 10000000;
        int id = r.nextInt(high-low) + low;
        String randomId = String.valueOf(id);
        loggedInUserEmail = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.e("TAG", "addItem: --------"+newItem);
            try{
                Map<String, Object> data = new HashMap<>();
                data.put(FIELD_Id,randomId);
                data.put(FIELD_flightName,newItem.get(0).getFlightName());
                data.put(FIELD_flightTo,newItem.get(0).getTo());
                data.put(FIELD_flightFrom,newItem.get(0).getFrom());
                data.put(FIELD_departureTiming,newItem.get(0).getDepartureTiming());
                data.put(FIELD_landingTiming,newItem.get(0).getLandingTiming());
                data.put(FIELD_totalTiming,newItem.get(0).getTotalTiming());
                data.put(FIELD_fare,newItem.get(0).getFare());
                data.put(FIELD_type,newItem.get(0).getType());
                data.put(FIELD_flightImage,newItem.get(0).getImageUrl());
                data.put(FIELD_departureDate,bookingData.getDepartureDate());
                data.put(FIELD_landingDate,bookingData.getLandingDate());
                data.put(FIELD_className,bookingData.getClassName());
                data.put(FIELD_numberOfChildren,bookingData.getNumberOfChildren());
                data.put(FIELD_numberOfAdults,bookingData.getNumberOfAdults());


                DB.collection(COLLECTION_FLIGHTDATA)
                        .document(loggedInUserEmail)
                        .collection(COLLECTION_DATA)
                        .document(randomId)
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(Tag, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(Tag,"onFailure: Document not added");
                            }
                        });
            }catch (Exception ex){
                Log.e(Tag,"addFriend"+ex.getLocalizedMessage());
            }
        }

//    public void addFriend(Friend newFriend){}

    public void deleteFlight(String docID){
        loggedInUserEmail = FirebaseAuth.getInstance().getCurrentUser().getUid();
        try{
            DB.collection(COLLECTION_FLIGHTDATA)
                    .document(loggedInUserEmail)
                    .collection(COLLECTION_DATA)
                    .document(docID)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e(Tag,"onSuccess: Document successfully deleted");
                            getAllFurnitureData();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(Tag,"onFailure: Unable to delete the document"+ e.getLocalizedMessage());
                        }
                    });
        }
        catch (Exception ex){
            Log.e(Tag,"getA;;Friends: Exception occured" + ex.getLocalizedMessage());
        }
    }
//
//    public void deleteFriend(Friend updatedFriend){
//
//        Map<String, Object> updatedInfo = new HashMap<>();
//        updatedInfo.put(FIELD_NAME, updatedFriend.getName());
//        updatedInfo.put(FIELD_PHONE, updatedFriend.getPhoneNumber());
//
//        try{
//            DB.collection(COLLECTION_USERS)
//                    .document(loggedInUserEmail)
//                    .collection(COLLECTION_FRIENDS)
//                    .document(updatedFriend.getId())
//                    .update(updatedInfo)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Log.e(Tag,"onSuccess: Document successfully updated");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.e(Tag,"onFailure: Unable to update the document"+ e.getLocalizedMessage());
//                        }
//                    });
//        }
//        catch (Exception ex){
//            Log.e(Tag,"getA;;Friends: Exception occured" + ex.getLocalizedMessage());
//        }
//    }
//
//    public void searchFriendByName(String name){
//        try{
//            DB.collection(COLLECTION_USERS).document(loggedInUserEmail)
//                    .collection(COLLECTION_FRIENDS)
//                    .whereEqualTo(FIELD_NAME,name)
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()){
//                                if(task.getResult().getDocuments().size() != 0){
//                                    Friend matchFriend = task.getResult().getDocuments().get(0).toObject(Friend.class);
//                                    matchFriend.setId(task.getResult().getDocuments().get(0).getId());
//
//                                    if (matchFriend != null){
//                                        Log.e(Tag,"onComplete:Found friend" + matchFriend.toString());
//                                        friendFromDB.postValue(matchFriend);
//                                    }else{
//                                        Log.e(Tag,"onComplete: unable to covet the matching document to Friend object" );
//                                    }
//
//                                }else {
//                                    Log.e(Tag,"onComplete: No friend with given nama found");
//                                }
//                            }
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.e(Tag,"onFailure: unable to find the friend with name"+ name + e.getLocalizedMessage());
//                        }
//                    });
//
//        }catch (Exception ex){
//            Log.e(Tag,"searchFriendByName: Exception occured"+ ex.getLocalizedMessage());
//        }
//    }

        public void getAllFurnitureData(){
            loggedInUserEmail = FirebaseAuth.getInstance().getCurrentUser().getUid();
            try{
                DB.collection(COLLECTION_FLIGHTDATA)
                        .document(loggedInUserEmail)
                        .collection(COLLECTION_DATA)
                        .orderBy(FIELD_Id, Query.Direction.ASCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                                if(error != null){
                                    Log.e(Tag,"onEvent: Unable to get document changes" + error);
                                    return;
                                }
                                List<TicketData> flightsList = new ArrayList<>();
                                if (snapshot != null){
                                    Log.e(Tag,"onEvent: Current changes" + snapshot.getDocumentChanges());
                                    flightsList.clear();
                                    for(DocumentChange documentChange: snapshot.getDocumentChanges()){

                                        TicketData currentFlight = documentChange.getDocument().toObject(TicketData.class);

//                                    currentFurniture.setId(documentChange.getDocument().getId());
                                        Log.e(Tag,"onEvent: Furniture Data: "+ currentFlight.toString());
                                        switch (documentChange.getType()){
                                            case ADDED:

                                                flightsList.add(currentFlight);
                                                break;
                                            case MODIFIED:

                                                break;
                                            case REMOVED:
                                                break;
                                        }
                                    }

                                    allFlights.postValue(flightsList);

                                }else{
                                    Log.e(Tag,"onEvent: No changes received");
                                }
                            }
                        });

            }
            catch (Exception ex){
                Log.e(Tag,"getA;;Friends: Exception occured"+ ex.getLocalizedMessage());
            }
        }


}
