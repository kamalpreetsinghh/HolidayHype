package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gbc.holidayhype.databinding.ActivityAboutBinding;
import com.gbc.holidayhype.databinding.ActivityFlightTicketBinding;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAboutBinding binding;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_flights);
        this.binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());


        binding.goBackToAccount3.setOnClickListener(this::onClick);
        binding.aboutDesc.setText("We are travelers and technologists. We work across time zones, hemispheres, cultures and languages. We’re used to breaking things down and building them back up again, until they’re even better. We know travel can be hard, but we also know that it’s worth it, every time. And because we believe travel is a force for good, we take our roles seriously. We’re here to build great products, and facilitate connections between travelers and our partners that truly bring good into the world.\n \n Whether it’s planning a family vacation, booking for business, or organizing the trip of a lifetime, \n" +
                "HolidayHype Group brands unlock the best possibilities for each individual traveler and each type of trip.\n" +
                "\n" +
                "Our investments in technology make it possible for us to deliver innovations at a faster pace so planning and booking becomes a more effortless and exciting part of the journey, wherever that leads you.");
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.go_backToAccount3: {
                    finish();
                    break;
                }
            }
        }
    }
}