package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gbc.holidayhype.databinding.ActivityCheckoutBinding;
import com.gbc.holidayhype.databinding.ActivityFlightConfirmationBinding;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityCheckoutBinding binding;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_home);
        this.binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.goToMainScreen.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.goToMainScreen: {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    break;
                }
            }
        }
    }
}