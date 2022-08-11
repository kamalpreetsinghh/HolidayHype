package com.gbc.holidayhype;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gbc.holidayhype.model.MyBooking;
import com.gbc.holidayhype.util.ServerUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateHotelBookingActivity extends AppCompatActivity {

    EditText tv_fullName,tv_email,tv_phoneNumber,tv_GuestName,tv_NumOfRooms;
    Dialog dialog;
    Button btn_update;
    String HotelBookingRefId;
    ImageView imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hotel_booking);
        tv_fullName = (EditText) findViewById(R.id.tv_fullName);
        tv_email= (EditText) findViewById(R.id.tv_email);
        tv_phoneNumber = (EditText) findViewById(R.id.tv_phoneNumber);
        tv_GuestName =(EditText) findViewById(R.id.tv_GuestName);
        tv_NumOfRooms =(EditText) findViewById(R.id.tv_NumOfRooms);
        btn_update =(Button)findViewById(R.id.btn_update);
        imageViewBack =(ImageView)findViewById(R.id.imageViewBack);
        SharedPreferences prefs = getSharedPreferences(ServerUtility.MY_PREFS_NAME, MODE_PRIVATE);
        HotelBookingRefId = prefs.getString(ServerUtility.HotelBookingRefId, "No RoomNo defined");

        getdatavolly();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    updateDataUsingVolley();
                }
            }
        });
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private Boolean validateData() {
        Boolean validData = true;

        if (this.tv_fullName.getText().toString().isEmpty()) {
            this.tv_fullName.setError("Full Name cannot be empty");
            validData = false;
        }
        if (this.tv_phoneNumber.getText().toString().isEmpty()) {
            this.tv_phoneNumber.setError("Phone cannot be empty");
            validData = false;
        }
        if (this.tv_email.getText().toString().isEmpty()) {
            this.tv_email.setError("Email cannot be empty");
            validData = false;
        }
        if (this.tv_GuestName.getText().toString().isEmpty()) {
            this.tv_GuestName.setError("Guest Name cannot be empty");
            validData = false;
        }
        if (this.tv_NumOfRooms.getText().toString().isEmpty()) {
            this.tv_NumOfRooms.setError("No. of Rooms cannot be empty");
            validData = false;
        }

        return validData;
    }
    private void updateDataUsingVolley() {
        dialog = new Dialog(UpdateHotelBookingActivity.this, R.style.TransparentBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_loading);
        dialog.setCancelable(false);
        dialog.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", tv_fullName.getText().toString());
        params.put("email", tv_email.getText().toString());
        params.put("phone", tv_phoneNumber.getText().toString());
        params.put("guestName", tv_GuestName.getText().toString());
        params.put("numberOfRooms", tv_NumOfRooms.getText().toString());
        String url = "https://holidayhype.herokuapp.com/api/hotelbooking/"+HotelBookingRefId;
        RequestQueue queue = Volley.newRequestQueue(UpdateHotelBookingActivity.this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject  response) {
                        Log.d("TAG", response.toString());
//                        msgResponse.setText(response.toString());
                        dialog.dismiss();
                        Log.v("responseUpdate", response.toString());
                        try {
                            Toast.makeText(UpdateHotelBookingActivity.this, "Update Succesfully", Toast.LENGTH_SHORT).show();
                            nextActivity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                dialog.dismiss();
                Log.v("responseinsert", error.toString());
                Toast.makeText(UpdateHotelBookingActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
        };

        // Adding request to request queue
        queue.add(jsonObjReq);
    }
    public void getdatavolly() {
        dialog = new Dialog(UpdateHotelBookingActivity.this, R.style.TransparentBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_loading);
        dialog.setCancelable(false);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://holidayhype.herokuapp.com/api/hotelbooking/"+HotelBookingRefId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                             dialog.dismiss();
                            Log.e("response", "Success == " + response.toString());
                            JSONObject tutorialsObject = new JSONObject(response);
                            MyBooking myBooking = new MyBooking();
                            myBooking.setHotelBookingRefId(tutorialsObject.getString("_id"));
                            myBooking.setName(tutorialsObject.getString("name"));
                            myBooking.setEmail(tutorialsObject.getString("email"));
                            myBooking.setPhone(tutorialsObject.getString("phone"));
                            myBooking.setGuestName(tutorialsObject.getString("guestName"));
                            myBooking.setNumberOfRooms(tutorialsObject.getString("numberOfRooms"));
                            myBooking.setUserID(tutorialsObject.getString("userID"));
                            myBooking.setHotelID(tutorialsObject.getString("hotelID"));
                            myBooking.setHotelName(tutorialsObject.getString("hotelName"));
                            myBooking.setCardNumber(tutorialsObject.getString("cardNumber"));
                            tv_fullName.setText(myBooking.getName());
                            tv_email.setText(myBooking.getEmail());
                            tv_phoneNumber.setText(myBooking.getPhone());
                            tv_GuestName.setText(myBooking.getGuestName());
                            tv_NumOfRooms.setText(myBooking.getNumberOfRooms());

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
    public  void nextActivity(){
        startActivity(new Intent(UpdateHotelBookingActivity.this,MyBookingActivity.class));
        finish();
    }
}