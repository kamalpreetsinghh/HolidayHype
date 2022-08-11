package com.gbc.holidayhype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.gbc.holidayhype.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = this.getClass().getCanonicalName();
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        this.binding =ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.binding.showPassword.setOnClickListener(this::onClick);

        this.binding.loginButton.setOnClickListener(view -> {
            this.validateData();
        });

        this.binding.signupButton.setOnClickListener(view -> {
            this.openSignupActivity();
        });

        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            openMainActivity();
            finish();
        }
    }

    private void validateData() {
        boolean validData = true;
        String email = this.binding.editTextEmail.getText().toString();
        String password = this.binding.editTextPassword.getText().toString();
        if (email.isEmpty()) {
            this.binding.editTextEmail.setError("Email cannot be left empty");
            validData = false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.binding.editTextEmail.setError("Email address is not valid");
            validData = false;
        }
        if (password.isEmpty()) {
            this.binding.editTextPassword.setError("Password cannot be left empty");
            validData = false;
        }
        if (validData) {
            this.signIn(email, password);
        } else {
            Toast.makeText(this, "Please enter correct inputs", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i(TAG, "Logged In");
                            openMainActivity();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication Failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void openSignupActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.showPassword: {
                    if (flag == 1){

                        this.binding.editTextPassword.setTransformationMethod(null);
                        this.binding.showPassword.setColorFilter(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_red_dark),
                                PorterDuff.Mode.SRC_ATOP);
                        flag = 0;
                    }else {
                        this.binding.editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                        this.binding.showPassword.setColorFilter(ContextCompat.getColor(getApplicationContext(), android.R.color.black),
                                PorterDuff.Mode.SRC_ATOP);
                        flag = 1;

                    }

        }
    }
}
    }
}