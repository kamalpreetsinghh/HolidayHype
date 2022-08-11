package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.VolleyError;
import com.gbc.holidayhype.adapter.FlightAdapter;
import com.gbc.holidayhype.adapter.FlightTicketAdapter;
import com.gbc.holidayhype.databinding.ActivityFlightTicketBinding;
import com.gbc.holidayhype.databinding.ActivityFlightsBinding;
import com.gbc.holidayhype.model.FlightApiData;
import com.gbc.holidayhype.model.FlightApiData2;
import com.gbc.holidayhype.model.FlightsData;
import com.gbc.holidayhype.model.TicketData;
import com.gbc.holidayhype.viewmodels.FlightViewModel;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FlightTicketActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityFlightTicketBinding binding;
    private final String TAG = "MainActivity";
    private FlightViewModel flightViewModel;
    private FlightTicketAdapter flightTicketAdapter;
    FlightApiData2 data = new FlightApiData2();
    RecyclerView flightTicketRecycler;
    private FlightApi flightApi;
    private volleyFlightInterface mResultCallback = null;
    ArrayList<FlightApiData2> flightDataList = new ArrayList<>();

    protected void onStart()
    {
        super.onStart();
        initVolleyCallback();
        flightApi = new FlightApi(mResultCallback,this);
        flightApi.fetchFlight("GetCall",this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_flights);
        this.binding = ActivityFlightTicketBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.flightViewModel = flightViewModel.getInstance(this.getApplication());
        binding.goBackToAccount.setOnClickListener(this::onClick);

//        this.flightViewModel.getAllFlightData();

//        this.flightViewModel.allFlights.observe(this, new Observer<List<TicketData>>() {
//            @Override
//            public void onChanged(List<TicketData> flightsItems) {
//                data.clear();
//
//                if (flightsItems != null){
//                    for(TicketData item : flightsItems){
//                        Log.e(TAG, "onChanged: "+flightsItems );
//                        data.add(item);
//
//                        flightTicketRecycler = findViewById(R.id.ticketsRecycler);
//                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
//                        flightTicketRecycler.setLayoutManager(layoutManager);
//                        flightTicketAdapter = new FlightTicketAdapter(getApplicationContext(), data);
//                        flightTicketRecycler.setAdapter(flightTicketAdapter);
//
//                    }
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.go_backToAccount: {
                    finish();
                    break;
                }
            }
        }
    }
    private void initVolleyCallback(){
        mResultCallback = new volleyFlightInterface() {
            @Override
            public void notifySuccess(String requestType, JSONArray response) {
                Log.e(TAG, "Volley requester " + requestType);
                Log.e(TAG, "Volley JSON post" + response);
                flightDataList.clear();

                    for (int i = 0 ; i < response.length() ; i ++) {

                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            String bookingId = jsonObject.getString("_id");
//                            JSONObject flightData = jsonObject.getJSONObject("flightID");
//                            String flightID = jsonObject.getString("flightID");
                            String userID = jsonObject.getString("userID");
                            String flightTo = jsonObject.getString("flightTo");
                            String flightFrom = jsonObject.getString("flightFrom");
                            String type = jsonObject.getString("type");
                            String departureDate = jsonObject.getString("departureDate");
                            String landingDate = jsonObject.getString("landingDate");
                            String className = jsonObject.getString("className");
                            String numberOfChildren = jsonObject.getString("numberOfChildren");
                            String numberOfAdults = jsonObject.getString("numberOfAdults");


                            JSONObject flightObject = jsonObject.getJSONObject("flightID");

                            String name =  flightObject.getString("name");
                            String imageUrl = flightObject.getString("imageUrl");



                            data = new FlightApiData2(bookingId,userID,flightTo,flightFrom,type,departureDate,landingDate,className,numberOfChildren,numberOfAdults,name,imageUrl);
                            flightDataList.add(data);
                            flightRecycler(flightDataList);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        flightTicketRecycler = findViewById(R.id.ticketsRecycler);
//                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
//                        flightTicketRecycler.setLayoutManager(layoutManager);
//                        flightTicketAdapter = new FlightTicketAdapter(getApplicationContext(), data);
//                        flightTicketRecycler.setAdapter(flightTicketAdapter);
                    }



            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }

    private void flightRecycler(ArrayList<FlightApiData2> flightContent){

        flightTicketRecycler = findViewById(R.id.ticketsRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        flightTicketRecycler.setLayoutManager(layoutManager);
        flightTicketAdapter = new FlightTicketAdapter(getApplicationContext(), flightContent);
        flightTicketRecycler.setAdapter(flightTicketAdapter);

    }
}