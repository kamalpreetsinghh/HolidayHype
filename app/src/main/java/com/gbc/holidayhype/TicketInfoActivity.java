package com.gbc.holidayhype;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.gbc.holidayhype.databinding.ActivityFlightsBinding;
import com.gbc.holidayhype.databinding.ActivityTicketInfoBinding;
import com.gbc.holidayhype.model.FlightApiData;
import com.gbc.holidayhype.model.FlightsData;
import com.gbc.holidayhype.model.TicketData;
import com.gbc.holidayhype.viewmodels.FlightViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TicketInfoActivity extends AppCompatActivity implements View.OnClickListener{

    AlertDialog.Builder builder;

    private ActivityTicketInfoBinding binding;
    private final String TAG = "MainActivity";
    ArrayList<TicketData> flightsData = new ArrayList<>();
    FlightViewModel flightViewModel;
    String Id = "";
    private FlightApi flightApi;
    private volleyFlightInterface2 mResultCallback = null;


    protected void onStart()
    {
        super.onStart();
        initVolleyCallback();
        flightApi = new FlightApi(mResultCallback,this,"unknown");
        Intent intent = getIntent();
        String flightId = intent.getStringExtra("flightID");
        flightApi.fetchSpecificFlight("GetCall",this,flightId);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_flights);
        this.binding = ActivityTicketInfoBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        builder = new AlertDialog.Builder(this);
        this.flightViewModel = FlightViewModel.getInstance(this.getApplication());

        binding.goBackToAccount2.setOnClickListener(this::onClick);
        binding.cancelFlight.setOnClickListener(this::onClick);

        Intent intent = getIntent();

//        flightsData = intent.getParcelableArrayListExtra("flightData");
//        Id = flightsData.get(0).getId();
//
//        binding.lbFirstPlaceName.setText(flightsData.get(0).getFrom());
//        binding.lbFirstSecondName.setText(flightsData.get(0).getTo());
//        binding.lbTotalTiming.setText(flightsData.get(0).getTotalTiming());
//        binding.lbFlightName.setText(flightsData.get(0).getFlightName());
//
//        binding.lbDepartureDate.setText(flightsData.get(0).getDepartureDate());
//        binding.lbLandingDate.setText(flightsData.get(0).getLandingDate());
//        binding.lbClassName.setText(flightsData.get(0).getClassName());
//
//        binding.lbNumberOfAdults.setText(flightsData.get(0).getNumberOfAdults());
//        binding.lbNumberOfAdults2.setText(flightsData.get(0).getNumberOfChildren());
//
//        Glide.with(getApplicationContext()).load(flightsData.get(0).getImageUrl()).into(this.binding.planeLogo);


    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.go_backToAccount2: {
                    finish();
                    break;
                }
                case R.id.cancel_Flight:{
                    Log.e(TAG, "onClick: Flight Id----------"+Id );
                    builder.setTitle("Alert!!")
                            .setMessage("Do you want to cancel this flight?")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    flightApi.deleteFlight(TicketInfoActivity.this,Id);
                                    finish();
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .show();



                    break;
                }
            }
        }
    }
    private void initVolleyCallback(){
        mResultCallback = new volleyFlightInterface2() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Log.e(TAG, "Volley requester " + requestType);
                Log.e(TAG, "Volley JSON post" + response);


                    try {


                        String mainFlightId = response.getString("_id");
                        Id = mainFlightId;

                        String userID = response.getString("userID");
                        String flightTo = response.getString("flightTo");
                        String flightFrom = response.getString("flightFrom");
                        String type = response.getString("type");
                        String departureDate = response.getString("departureDate");
                        String landingDate = response.getString("landingDate");
                        String className = response.getString("className");
                        String numberOfChildren = response.getString("numberOfChildren");
                        String numberOfAdults = response.getString("numberOfAdults");

                        JSONObject flightObject = response.getJSONObject("flightID");

                        String name =  flightObject.getString("name");
                        String totalTiming = flightObject.getString("totalTiming");
                        String imageUrl = flightObject.getString("imageUrl");

                        binding.lbFirstPlaceName.setText(flightFrom);
                        binding.lbFirstSecondName.setText(flightTo);
                        binding.lbTotalTiming.setText(totalTiming);
                        binding.lbFlightName.setText(name);

                        binding.lbDepartureDate.setText(departureDate);
                        binding.lbLandingDate.setText(landingDate);
                        binding.lbClassName.setText(className);

                        binding.lbNumberOfAdults.setText(numberOfAdults);
                        binding.lbNumberOfAdults2.setText(numberOfChildren);

                        Glide.with(getApplicationContext()).load(imageUrl).into(binding.planeLogo);

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };
    }
}