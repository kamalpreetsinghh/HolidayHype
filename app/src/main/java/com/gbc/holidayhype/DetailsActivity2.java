package com.gbc.holidayhype;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gbc.holidayhype.R;
import com.gbc.holidayhype.databinding.ActivityDetailsBinding;
import com.gbc.holidayhype.model.AttractionData;
import com.gbc.holidayhype.model.TopPlacesData;

import java.util.ArrayList;

public class DetailsActivity2 extends AppCompatActivity implements View.OnClickListener {

    private ActivityDetailsBinding binding;
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
        this.binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.goBack.setOnClickListener(this::onClick);
        binding.startBookingTrip.setOnClickListener(this::onClick);

        Intent currentIntent = this.getIntent();

        AttractionData TopPlaceData = currentIntent.getParcelableExtra("TopPlaceData");

        if(TopPlaceData == null){

            Log.d(TAG, "onCreate: Not Working");
        }else {
//            this.binding.imageView3.setImageResource(TopPlaceData.get(0).getPlaceImage());


            Glide.with(this).load(TopPlaceData.getPlaceImage()).into(this.binding.imageView3);

            placeName = TopPlaceData.getCountry() +", "+ TopPlaceData.getPlaceName();
            placeNameForIntent = TopPlaceData.getCountry();
            this.binding.textView6.setText(placeName);
            this.binding.textView14.setText(TopPlaceData.getDescription());
            Glide.with(this).load(TopPlaceData.getVisitPlace1()).into(this.binding.imageView8);
            Glide.with(this).load(TopPlaceData.getVisitPlace2()).into(this.binding.imageView9);
            Glide.with(this).load(TopPlaceData.getVisitPlace3()).into(this.binding.imageView10);
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
