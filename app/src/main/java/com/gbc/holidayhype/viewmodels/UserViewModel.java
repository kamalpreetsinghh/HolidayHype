package com.gbc.holidayhype.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gbc.holidayhype.model.User;
import com.gbc.holidayhype.repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private static UserViewModel instance;
    private final UserRepository userRepository = new UserRepository();

    public MutableLiveData<User> user;
    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public static UserViewModel getInstance(Application application){
        if (instance == null){
            instance = new UserViewModel(application);
        }
        return instance;
    }

    public MutableLiveData<User> getUserDetails(String userID){
        this.userRepository.getUserDetails(userID);
        return userRepository.user;
    }

    public void addUserDetails(User user) {

        this.userRepository.addUser(user);
    }

    public void getAllUserData(){
        this.userRepository.getAllUserData();
        this.user = this.userRepository.user;
    }
    public void updateUser(User updatedObject){
        this.userRepository.updateUser(updatedObject);
    }

}
