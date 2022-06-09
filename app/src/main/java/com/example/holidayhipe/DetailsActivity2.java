package com.example.holidayhipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.holidayhipe.databinding.ActivityDetailsBinding;
import com.example.holidayhipe.model.RecentsData;
import com.example.holidayhipe.model.TopPlacesData;

import java.util.ArrayList;

public class DetailsActivity2 extends AppCompatActivity implements View.OnClickListener {

    private ActivityDetailsBinding binding;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_home);
        this.binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.imageView4.setOnClickListener(this::onClick);

        Intent currentIntent = this.getIntent();


        ArrayList<TopPlacesData> TopPlaceData = currentIntent.getParcelableArrayListExtra("TopPlaceData");

        if(TopPlaceData.isEmpty()){

            Log.d(TAG, "onCreate: Not Working");
        }else {
            this.binding.imageView3.setImageResource(TopPlaceData.get(0).getImageUrl());
            String placename = TopPlaceData.get(0).getPlaceName() +", "+ TopPlaceData.get(0).getCountryName();
            this.binding.textView6.setText(placename);
            Log.d(TAG, "Data : "+ TopPlaceData);
        }


    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.imageView4: {
                    finish();
                }
            }
        }
    }
}
