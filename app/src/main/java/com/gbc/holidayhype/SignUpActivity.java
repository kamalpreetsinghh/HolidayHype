package com.gbc.holidayhype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gbc.holidayhype.databinding.ActivitySignUpBinding;
import com.gbc.holidayhype.model.User;
import com.gbc.holidayhype.repositories.UserRepository;
import com.gbc.holidayhype.viewmodels.FlightViewModel;
import com.gbc.holidayhype.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getCanonicalName();
    private ActivitySignUpBinding binding;
    private UserViewModel userViewModel;
    private UserRepository userRepository;
    private FirebaseAuth mAuth;
    private UserApi userApi = new UserApi();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        this.binding =ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        this.userViewModel = UserViewModel.getInstance(this.getApplication());

        this.userViewModel = UserViewModel.getInstance(this.getApplication());

        this.binding.goBackToLogin.setOnClickListener(this::onClick);
        this.binding.createAccountButton.setOnClickListener(view -> {
            this.validateData();
        });

        mAuth = FirebaseAuth.getInstance();
    }

    private void validateData() {
        boolean validData = true;

        user = new User();
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

        if (user.getPassword().isEmpty()) {
            this.binding.password.setError("Password cannot be left empty");
            validData = false;
        }

        if(confirmPassword.getText().toString().isEmpty()) {
            confirmPassword.setError("Confirm Password cannot be left empty");
            validData = false;
        }

        if(!user.getPassword().equals(confirmPassword.getText().toString())) {
            confirmPassword.setError("Confirm Password does not match with the Password");
            validData = false;
        }

        if (validData) {

//        openMainActivity();
            this.createAccount(user);
//            postDataUsingVolley();
        } else {
            Toast.makeText(this, "Please enter correct inputs", Toast.LENGTH_SHORT).show();
        }

    }

    private void createAccount(User user) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user.setUserID(mAuth.getCurrentUser().getUid());
                            userViewModel.addUserDetails(user);
//                            postDataUsingVolley();

                            userApi.addUser(SignUpActivity.this,user);
                            openMainActivity();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END create_user_with_email]
    }




private void postDataUsingVolley() {
        // url to post our data
        Random r = new Random();
        int low = 0;
        int high = 10000;
        int id = r.nextInt(high-low) + low;
        String randomId = String.valueOf(1);
        String url = "https://holidayhype.herokuapp.com/api/user/insert";
//        loadingPB.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                loadingPB.setVisibility(View.GONE);
                Log.v("Data added to api", response.toString());

                Toast.makeText(SignUpActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.v("Data not added to api", error.toString());
                Toast.makeText(SignUpActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                Log.e(TAG, "getParams: --------"+user.getUserID() );

                params.put("userID",user.getUserID());
                params.put("firstName", user.getFirstName());
                params.put("lastName", user.getLastName());
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void openMainActivity() {
//
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.go_BackToLogin: {
                    finish();
                }
            }
        }
    }
}