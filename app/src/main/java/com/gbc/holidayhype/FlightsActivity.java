package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.gbc.holidayhype.R;
import com.gbc.holidayhype.adapter.FlightAdapter;
import com.gbc.holidayhype.databinding.ActivityFlightsBinding;
import com.gbc.holidayhype.model.AttractionData;
import com.gbc.holidayhype.model.BookingData;
import com.gbc.holidayhype.model.FlightsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FlightsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityFlightsBinding binding;
    private final String TAG = "MainActivity";
    String firstPlace = "";
    String secondPlace = "";
    String type = "Non-Stop";

    RecyclerView flightRecycler;
    FlightAdapter flightAdapter;
    BookingData bookingData;

    private RequestQueue requestQueue;

    ArrayList<FlightsData> flightList = new ArrayList<FlightsData>();


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

        bookingData = intent.getParcelableExtra("bookingArray");


        firstPlace = bookingData.getDeparturePlace();
        secondPlace = bookingData.getLandingPlace();

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        fetchAttractionPlaces();

        ArrayList<FlightsData> allFlightsDataList = new ArrayList<>();

//        ArrayList<FlightsData> flightsDataList = new ArrayList<>();
//        flightsDataList.add(new FlightsData("AirCanada","Toronto","Calgary","14:00","20:00","6 hr","June17","$350","Non-Stop", R.drawable.planeaircanada));
//        flightsDataList.add(new FlightsData("AirCanada","Toronto","Calgary","12:00","17:20","5 hr 20 min","June17","$420","2 Stops",R.drawable.planeaircanada));
//        flightsDataList.add(new FlightsData("WestJet","Toronto","Calgary","15:10","23:40","8 hr 30 min","June17","$500","1 Stops",R.drawable.planewestjet));
//        flightsDataList.add(new FlightsData("WestJet","Montreal","Calgary","09:00","13:00","4hr","June17","$290","Non-Stop",R.drawable.planewestjet));
//        flightsDataList.add(new FlightsData("Air Transat","Toronto","Calgary","11:40","15:30","3hr 50 min","June17","$330","3 Stops",R.drawable.planetransat));
//        flightsDataList.add(new FlightsData("Air Transat","Montreal","Calgary","17:00","21:30","4 hr 30 min","June17","$570","Non-Stop",R.drawable.planetransat));
//        flightsDataList.add(new FlightsData("Jazz Aviation","Toronto","Calgary","08:10","11:15","3 hr 15 min","June17","$410","1 Stops",R.drawable.planejazz));
//        flightsDataList.add(new FlightsData("Jazz Aviation","Toronto","Calgary","05:30","10:18","4hr 48 min","June17","$280","Non-Stop",R.drawable.planejazz));

//        for (int i=0;i<flightsDataList.size();i++){
//            if (flightsDataList.get(i).getFrom().equals(firstPlace) && flightsDataList.get(i).getTo().equals(secondPlace)){
//                allFlightsDataList.add(flightsDataList.get(i));
//            }
//        }
        setFlightRecycler(allFlightsDataList);
    }

    private void setFlightRecycler(ArrayList<FlightsData> flightsDataList) {

        flightRecycler = findViewById(R.id.flight_Recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        flightRecycler.setLayoutManager(layoutManager);
        flightAdapter = new FlightAdapter(this, flightsDataList,bookingData);
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

    private void fetchAttractionPlaces() {

        String url = "https://holidayhype.herokuapp.com/api/flights/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        Log.e("TAG", "onResponse: "+response );
                        for (int i = 0 ; i < response.length() ; i ++){

                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                String id = jsonObject.getString("_id");
                                String name = jsonObject.getString("name");
                                String departureTiming = jsonObject.getString("departureTiming");
                                String landingTiming = jsonObject.getString("landingTiming");
                                String totalTiming = jsonObject.getString("totalTiming");
                                String fare = jsonObject.getString("fare");
                                String imageUrl = jsonObject.getString("imageUrl");

                                 FlightsData data = new FlightsData(id,name,firstPlace,secondPlace,departureTiming,landingTiming,totalTiming,fare,type,imageUrl);


                                flightList.add(data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            MovieAdapter adapter = new MovieAdapter(MainActivity.this , movieList);
//

                       setFlightRecycler(flightList);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FlightsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(jsonArrayRequest);
    }
}