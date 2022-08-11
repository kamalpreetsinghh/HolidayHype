package com.gbc.holidayhype;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gbc.holidayhype.adapter.MyBookingAdapter;
import com.gbc.holidayhype.model.MyBooking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MyBookingActivity extends AppCompatActivity {
    private RecyclerView ListDataView;
    private MyBookingAdapter Adapter;
    ImageView menu, profile;
    TextView title;
    ArrayList<MyBooking> roomModelArrayList = new ArrayList<>();
    Dialog dialog;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide (); //This Line hides the action bar
        setContentView(R.layout.activity_view_all);
        title = findViewById(R.id.pageTitle);

        menu = findViewById(R.id.onMenu);
        profile= findViewById(R.id.onProfile);
        title.setText("My Booking");

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent = new Intent(CurrentBooking.this, UserMenu.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(CurrentBooking.this, UserProfile.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
            }
        });
        ListDataView = findViewById(R.id.AllListView);

//        BookingFetchData roomFetchData = new BookingFetchData(this, this);
          RecyclerViewMethod();
//        roomFetchData.onSuccessUpdate(this);

    }
    @Override
    public void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // No user is signed in
            Log.v("UserId","not found");
        } else {
            Log.v("UserId",currentUser.getUid());
        }
    }
    public void RecyclerViewMethod() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        ListDataView.setLayoutManager(manager);
        ListDataView.setItemAnimator(new DefaultItemAnimator());
        ListDataView.setHasFixedSize(true);
        getdatavolly();
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
//        ListDataView.setLayoutManager(mLayoutManager);
//        roomModelArrayList = getArrayList("bookingData");

    }

    public void getdatavolly() {
        dialog = new Dialog(MyBookingActivity.this, R.style.TransparentBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_loading);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://holidayhype.herokuapp.com/api/hotelbooking/user/5673",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            Log.e("responsemybooking", "Success == " + response.toString());
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject tutorialsObject = jsonArray.getJSONObject(i);
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
                                roomModelArrayList.add(myBooking);
                            }
                            Adapter = new MyBookingAdapter(MyBookingActivity.this, roomModelArrayList);
                            ListDataView.setAdapter(Adapter);
                            ListDataView.invalidate();
                            Log.e("ListDataView", "hello" + roomModelArrayList.size());
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
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(CurrentBooking.this, HomePageActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
        finish();
    }


}