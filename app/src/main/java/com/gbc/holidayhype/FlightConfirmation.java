package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.gbc.holidayhype.databinding.ActivityFlightConfirmationBinding;
import com.gbc.holidayhype.databinding.ActivityHomeBinding;
import com.gbc.holidayhype.model.BookingData;
import com.gbc.holidayhype.model.FlightsData;

import java.util.ArrayList;
import java.util.Random;

public class FlightConfirmation extends AppCompatActivity implements View.OnClickListener {

    private ActivityFlightConfirmationBinding binding;
    private static final String TAG = "MainActivity";
    BookingData bookingData;
    ArrayList<FlightsData> flightsData = new ArrayList<>();
    String[] Terminal={"A", "B", "C", "D", "E", "F"};
    String[] Gates={"121", "433", "116", "209", "91", "019"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_home);
        this.binding = ActivityFlightConfirmationBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.goBackToPage.setOnClickListener(this::onClick);
        binding.bookFlight.setOnClickListener(this::onClick);

        Intent intent = getIntent();

        bookingData = intent.getParcelableExtra("bookingArray");
        flightsData = intent.getParcelableArrayListExtra("flightData");

        Log.e(TAG, "onCreate booking data: "+bookingData);
        Log.e(TAG, "onCreate booking data: "+flightsData);

        binding.firstAirport.setText(bookingData.getDeparturePlace());
        binding.secondAirport.setText(bookingData.getLandingPlace());
        binding.textView29.setText(bookingData.getClassName());
        binding.textView32.setText(bookingData.getNumberOfChildren());
        binding.textView35.setText(bookingData.getNumberOfAdults());

        Random r=new Random();
        int randomNumber=r.nextInt(Terminal.length);
        int randomNumber2=r.nextInt(Gates.length);

        binding.textView30.setText(Terminal[randomNumber]);
        binding.textView31.setText(Gates[randomNumber2]);

        binding.textView23.setText(flightsData.get(0).getFlightName());
        Glide.with(getApplicationContext()).load(flightsData.get(0).getImageUrl()).into(this.binding.planeLogo);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.go_backToPage: {
                    finish();
                    break;
                }
                case R.id.bookFlight: {
                    Intent i = new Intent(this, PaymentActivity.class);
                    i.putParcelableArrayListExtra("flightData", flightsData);
                    i.putExtra("bookingArray",bookingData);
                    startActivity(i);
                    break;

                }
            }
        }
    }
}