package com.example.holidayhipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.holidayhipe.adapter.FlightAdapter;
import com.example.holidayhipe.adapter.RecentsAdapter;
import com.example.holidayhipe.adapter.TopPlacesAdapter;
import com.example.holidayhipe.databinding.ActivityFlightsBinding;
import com.example.holidayhipe.databinding.ActivityHomeBinding;
import com.example.holidayhipe.model.FlightsData;
import com.example.holidayhipe.model.RecentsData;
import com.example.holidayhipe.model.TopPlacesData;

import java.util.ArrayList;
import java.util.List;

public class FlightsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityFlightsBinding binding;
    private final String TAG = "MainActivity";

    RecyclerView flightRecycler;
    FlightAdapter flightAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_flights);
        this.binding = ActivityFlightsBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.backBtn.setOnClickListener(this::onClick);

        Intent intent = getIntent();
        String firstPlace = intent.getStringExtra("from");
        String secondPlace = intent.getStringExtra("to");

        ArrayList<FlightsData> allFlightsDataList = new ArrayList<>();

        ArrayList<FlightsData> flightsDataList = new ArrayList<>();
        flightsDataList.add(new FlightsData("AirCanada","Toronto","Calgary","14:00","20:00","6 hr","June17","$350","Non-Stop",R.drawable.planeaircanada));
        flightsDataList.add(new FlightsData("AirCanada","Toronto","Calgary","12:00","17:20","5 hr 20 min","June17","$420","2 Stops",R.drawable.planeaircanada));
        flightsDataList.add(new FlightsData("WestJet","Toronto","Calgary","15:10","23:40","8 hr 30 min","June17","$500","1 Stops",R.drawable.planewestjet));
        flightsDataList.add(new FlightsData("WestJet","Montreal","Calgary","09:00","13:00","4hr","June17","$290","Non-Stop",R.drawable.planewestjet));
        flightsDataList.add(new FlightsData("Air Transat","Toronto","Calgary","11:40","15:30","3hr 50 min","June17","$330","3 Stops",R.drawable.planetransat));
        flightsDataList.add(new FlightsData("Air Transat","Montreal","Calgary","17:00","21:30","4 hr 30 min","June17","$570","Non-Stop",R.drawable.planetransat));
        flightsDataList.add(new FlightsData("Jazz Aviation","Toronto","Calgary","08:10","11:15","3 hr 15 min","June17","$410","1 Stops",R.drawable.planejazz));
        flightsDataList.add(new FlightsData("Jazz Aviation","Toronto","Calgary","05:30","10:18","4hr 48 min","June17","$280","Non-Stop",R.drawable.planejazz));

        for (int i=0;i<flightsDataList.size();i++){
            if (flightsDataList.get(i).getFrom().equals(firstPlace) && flightsDataList.get(i).getTo().equals(secondPlace)){
                allFlightsDataList.add(flightsDataList.get(i));
            }
        }

        setFlightRecycler(allFlightsDataList);
    }

    private void setFlightRecycler(ArrayList<FlightsData> flightsDataList) {

        flightRecycler = findViewById(R.id.flight_Recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        flightRecycler.setLayoutManager(layoutManager);
        flightAdapter = new FlightAdapter(this, flightsDataList);
        flightRecycler.setAdapter(flightAdapter);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.backBtn: {
                    finish();
                    break;
                }
            }
        }
    }
}