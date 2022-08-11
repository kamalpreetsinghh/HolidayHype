package com.gbc.holidayhype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gbc.holidayhype.adapter.FlightTicketAdapter;
import com.gbc.holidayhype.databinding.ActivityTicketInfoBinding;
import com.gbc.holidayhype.databinding.ActivityUpdateUserInfoBinding;
import com.gbc.holidayhype.model.TicketData;
import com.gbc.holidayhype.model.User;
import com.gbc.holidayhype.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateUserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityUpdateUserInfoBinding binding;
    private final String TAG = "MainActivity";
    private UserViewModel userViewModel;
    private int flag = 0;
    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_flights);
        this.binding = ActivityUpdateUserInfoBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

       changeState(false);

        this.userViewModel = UserViewModel.getInstance(this.getApplication());

//        this.userViewModel.getAllUserData();
//        this.userViewModel.user.observe(this, new Observer<User>() {
//            @Override
//            public void onChanged(User userData) {
//
//
//                if (userData != null){
//                    Log.e(TAG, "onChanged: "+userData.getEmail() );
//
//                    binding.firstName.setText(userData.getFirstName());
//                    binding.lastName.setText(userData.getLastName());
//                    binding.emailAddress.setText(userData.getEmail());
//
//
//                }
//            }
//        });
        binding.goBackToAccount4.setOnClickListener(this::onClick);
        binding.updateProfile.setOnClickListener(this::onClick);
        binding.updatePassword.setOnClickListener(this::onClick);
    }

    private void validateData() {
        boolean validData = true;

       String loggedInUserEmail = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User user = new User();
        user.setUserID(loggedInUserEmail);
        user.setFirstName(this.binding.firstName.getText().toString());
        user.setLastName(this.binding.lastName.getText().toString());
        user.setEmail(this.binding.emailAddress.getText().toString());
        user.setPassword(this.binding.password.getText().toString());

        EditText confirmPassword = this.binding.confirmPassword;

        if (user.getFirstName().isEmpty()) {
            this.binding.firstName.setError("First Name cannot be left empty");
            validData = false;
        }

        if (user.getLastName().isEmpty()) {
            this.binding.lastName.setError("Last Name cannot be left empty");
            validData = false;
        }

        if (user.getEmail().isEmpty()) {
            this.binding.emailAddress.setError("Email cannot be left empty");
            validData = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            this.binding.emailAddress.setError("Please enter the correct email");
            validData = false;
        }

        if (flag == 1) {


            if (user.getPassword().isEmpty() || user.getPassword().length() < 6) {
                this.binding.password.setError("Password cannot be left empty");
                validData = false;
            }

            if (confirmPassword.getText().toString().isEmpty()) {
                confirmPassword.setError("Confirm Password cannot be left empty");
                validData = false;
            }

            if (!user.getPassword().equals(confirmPassword.getText().toString())) {
                confirmPassword.setError("Confirm Password does not match with the Password");
                validData = false;
            }
        }

        if (validData) {

            userApi.updateUser(UpdateUserInfoActivity.this,user);


            if(flag == 1){
                FirebaseAuth.getInstance().getCurrentUser().updatePassword(user.getPassword()).addOnSuccessListener(new OnSuccessListener<Void>() {
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
            FirebaseAuth.getInstance().getCurrentUser().updateEmail(user.getEmail()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e("TAG","onSuccess: Document successfully updated");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG","onFailure: Unable to update the document"+ e.getLocalizedMessage());
                        }
                    });


            finish();

        } else {
            Toast.makeText(this, "Please enter correct inputs", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.go_backToAccount4: {
                    finish();
                    break;
                }
                case R.id.updateProfile: {
                    validateData();
                    break;
                }
                case R.id.updatePassword: {
                    if (flag == 1){
                        changeState(false);
                        flag = 0;
                    }else {
                        changeState(true);
                        flag = 1;
                    }

                    break;
                }
            }
        }
    }

    private void changeState(Boolean state){
        Drawable border1 = getResources().getDrawable( R.drawable.button_border );
        Drawable border2 = getResources().getDrawable( R.drawable.button_border2 );
        if (state == false){
            binding.password.setBackground(border2);
            binding.confirmPassword.setBackground(border2);
        }else {
            binding.password.setBackground(border1);
            binding.confirmPassword.setBackground(border1);
        }
        binding.password.setEnabled(state);
        binding.confirmPassword.setEnabled(state);
    }

}