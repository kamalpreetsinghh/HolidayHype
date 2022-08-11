package com.gbc.holidayhype;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.gbc.holidayhype.R;
import com.gbc.holidayhype.model.RecentsData;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private com.gbc.holidayhype.databinding.ActivityDetailsBinding binding;
    private static final String TAG = "MainActivity";

    private String placeName;
    private String placeNameForIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_home);
        this.binding = com.gbc.holidayhype.databinding.ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.goBack.setOnClickListener(this::onClick);
        binding.startBookingTrip.setOnClickListener(this::onClick);

        Intent currentIntent = this.getIntent();

        ArrayList<RecentsData> data = currentIntent.getParcelableArrayListExtra("placeData");


        if(data.isEmpty()){

            Log.d(TAG, "onCreate: Not Working");
        }else {
            this.binding.imageView3.setImageResource(data.get(0).getImageUrl());
            placeName = data.get(0).getPlaceName() +", "+ data.get(0).getCountryName();
            placeNameForIntent = data.get(0).getPlaceName();
            this.binding.textView6.setText(placeName);
            Log.d(TAG, "Data : "+ data);
        }
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.go_Back: {
                    finish();
                    break;
                }
                case R.id.startBookingTrip: {
                    Intent i = new Intent(this, BookingActivity.class);
                    i.putExtra("destination",placeNameForIntent);
                    startActivity(i);
//                    startActivity(new Intent(getApplicationContext(),BookingActivity.class));
                    break;
                }
            }
        }
    }
}