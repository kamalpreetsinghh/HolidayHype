package com.gbc.holidayhype;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gbc.holidayhype.adapter.HotelAndResortAdapter;
import com.gbc.holidayhype.model.HotelAndResort;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HotelsFragment extends AppCompatActivity {

    ProgressDialog dialog;
    RecyclerView listViewHotelAndResort;
    ImageView imageViewBack,imageViewFilter,imageViewBooking;
    ArrayList<HotelAndResort> hotelArrayList,hotelTempArrayList;
    TextView textViewNoData,textViewHeader;
    SharedPreferences sharedPreferences;
    EditText editTextSearch;
    String value;
    private String mParam1;
    private String mParam2;
    RelativeLayout dateButton;
    public static TextView checkInText;
    int LAUNCH_SECOND_ACTIVITY = 1;
    TextView searchButton;
    public static final int REQUEST_CODE = 11;
    public static final int RESULT_CODE = 12;
    public static final String EXTRA_KEY_TEST = "testKey";
    public HotelsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_hotels);
        hotelArrayList=new ArrayList<HotelAndResort>();
        dateButton=(RelativeLayout)findViewById(R.id.dateButton);
        checkInText = (TextView) findViewById(R.id.checkInText);
        hotelTempArrayList=new ArrayList<HotelAndResort>();
        editTextSearch=(EditText)findViewById(R.id.editTextSearch);
        imageViewBack=(ImageView)findViewById(R.id.imageViewBack);
        imageViewFilter=(ImageView)findViewById(R.id.imageViewFilter);
        textViewNoData=(TextView)findViewById(R.id.textViewNoData);
        textViewHeader=(TextView)findViewById(R.id.textViewTitle);
        listViewHotelAndResort=(RecyclerView) findViewById(R.id.recyclerView);
        imageViewBooking = (ImageView)findViewById(R.id.imageViewBooking);
        searchButton = (TextView)findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInText.getText().toString()!=""){
                    hotelArrayList.clear();
                    getdata();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please Select Date",Toast.LENGTH_SHORT).show();
                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HotelsFragment.this, CalenderRangeActivity.class);
//                MainActivity.resultLauncher.launch(intent);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageViewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(HotelsFragment.this,CurrentBookingActivity.class);
//                startActivity(intent);
                Intent intent=new Intent(HotelsFragment.this,MyBookingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable S) {
                if (editTextSearch.getText().toString().length()>0){
                    hotelTempArrayList.clear();
                    for(int i=0;i<hotelArrayList.size();i++){
                        if (hotelArrayList.get(i).getName().toLowerCase().startsWith(editTextSearch.getText().toString().toLowerCase()))
                            hotelTempArrayList.add(hotelArrayList.get(i));
                    }

                }
                else {
                    hotelTempArrayList.clear();
                    hotelTempArrayList.addAll(hotelArrayList);
                }
                listViewHotelAndResort.setAdapter(new HotelAndResortAdapter(HotelsFragment.this,hotelTempArrayList));

            }
        });
    }
public  void getdata(){
    dialog = new ProgressDialog(HotelsFragment.this);
    dialog.setMessage("Please wait...");
    dialog.setCancelable(false);
    dialog.show();
    StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://holidayhype.herokuapp.com/api/hotels",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        dialog.dismiss();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject tutorialsObject = jsonArray.getJSONObject(i);
                               Log.e("APIRes", "Success == " + tutorialsObject.getString("name"));
                            HotelAndResort hotelAndResort=new HotelAndResort();
                            hotelAndResort.setId(tutorialsObject.getString("_id"));
                            hotelAndResort.setName(tutorialsObject.getString("name"));
                            hotelAndResort.setAddress(tutorialsObject.getString("address"));
                            hotelAndResort.setDescription(tutorialsObject.getString("description"));
                            hotelAndResort.setPrice(tutorialsObject.getString("price"));
                            hotelAndResort.setImageUrl(tutorialsObject.getString("imageUrl"));
                            hotelAndResort.setType(tutorialsObject.getString("type"));
                            hotelAndResort.setLatitude(tutorialsObject.getString("latitude"));
                            hotelAndResort.setLongitude(tutorialsObject.getString("longitude"));
                            hotelAndResort.setDate(tutorialsObject.getString("date"));
                            hotelAndResort.setNoOFRooms(tutorialsObject.getString("numberOfRooms"));
                            if(hotelAndResort.getNoOFRooms() == "0"){

                            }
                            hotelArrayList.add(hotelAndResort);
                            if (hotelArrayList.size()>0){

                                hotelTempArrayList.clear();
                                hotelTempArrayList.addAll(hotelArrayList);
                                HotelAndResortAdapter videoRecentAdapter = new HotelAndResortAdapter(HotelsFragment.this,hotelArrayList);
                                listViewHotelAndResort.setHasFixedSize(true);
                                listViewHotelAndResort.setLayoutManager(new LinearLayoutManager(HotelsFragment.this, RecyclerView.VERTICAL, false));
                                listViewHotelAndResort.setAdapter(videoRecentAdapter);
                                textViewNoData.setVisibility(View.GONE);
                                listViewHotelAndResort.setVisibility(View.VISIBLE);
                            }
                            else {
                                listViewHotelAndResort.setVisibility(View.GONE);
                                textViewNoData.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("catch", "" + e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("catch", "" + error.getMessage());
            dialog.dismiss();
        }
    });
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);

}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            String testResult = data.getStringExtra(EXTRA_KEY_TEST);
            Date date = new Date(testResult);
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
//            mTimeText.setText("Time: " + dateFormat.format(date));
            checkInText.setText(dateFormat.format(date));
            // TODO: Do something with your extra data
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hotelArrayList.clear();
        getdata();
    }
}