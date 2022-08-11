package com.gbc.holidayhype.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gbc.holidayhype.model.BookingData;
import com.gbc.holidayhype.model.FlightsData;
import com.gbc.holidayhype.model.TicketData;
import com.gbc.holidayhype.repositories.FlightRepository;

import java.util.ArrayList;
import java.util.List;

public class FlightViewModel extends AndroidViewModel{

        private static FlightViewModel outInstance;
        private final FlightRepository flightRepository = new FlightRepository();
        public MutableLiveData<List<TicketData>> allFlights;

        public FlightViewModel(Application application) {
            super(application);
//        this.friendRepository.getAllFriends();
        }

        public static FlightViewModel getInstance(Application application){
            if (outInstance == null){
                outInstance = new FlightViewModel(application);
            }
            return outInstance;
        }
        public FlightRepository get()
        {
            return this.flightRepository;
        }


        public void addItem(ArrayList<FlightsData> newItem, BookingData bookingData) {
            this.flightRepository.addItem(newItem,bookingData);
        }

        public void getAllFlightData(){
            this.flightRepository.getAllFurnitureData();
            this.allFlights = this.flightRepository.allFlights;
        }
//    public void searchFriendByName(String friendName){
//        this.friendRepository.searchFriendByName(friendName);
//    }
//
    public void deleteFlight(String docId){
        this.flightRepository.deleteFlight(docId);
    }
//    public void updateFriend(Friend updatedObject){
//        this.friendRepository.deleteFriend(updatedObject);
//    }


}
