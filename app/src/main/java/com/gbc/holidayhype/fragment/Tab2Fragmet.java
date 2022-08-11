package com.gbc.holidayhype.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fevziomurtekin.payview.Payview;
import com.fevziomurtekin.payview.data.PayModel;
import com.gbc.holidayhype.HotelBookingActivity;
import com.gbc.holidayhype.R;
import com.gbc.holidayhype.model.DetailedHotel;
import com.gbc.holidayhype.util.ServerUtility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Tab2Fragmet extends Fragment {
    DetailedHotel getDetailedHotelFragment;
    String fullNameFrag, phoneNumberFrag, EmaildFrag, GuestNameFrag, noOfRoomsFrag,CardNumber;
    int updatedRoom;
    TextView tv_demo;
    Payview payview;
    SendMessagetab2 SM2;
    FirebaseUser currentUser;
    public Tab2Fragmet() {
        // Required empty public constructor
    }
    public Tab2Fragmet(DetailedHotel getDetailedHotel) {
        getDetailedHotelFragment = getDetailedHotel;

    }
    public  interface SendMessagetab2 {
        void sendBookingData(String fullName,String HotelName,String HotelBookingId,String EmailId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            SM2 = (SendMessagetab2) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2_fragmet, container, false);
         payview = view.findViewById(R.id.payview);
//        payview.setBackgroundColor(getResources().getColor(R.color.colorRed));
         payview.setOnDataChangedListener(new Payview.OnChangelistener() {
             @Override
             public void onChangelistener(@Nullable PayModel payModel, boolean b) {
                 CardNumber = payModel.getCardNo();
             }
         });
        payview.setPayOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getActivity().getSharedPreferences(ServerUtility.MY_PREFS_NAME, MODE_PRIVATE);
                String hotelRoom = prefs.getString(ServerUtility.HotelRoom, "No RoomNo defined");
                updatedRoom = Integer.parseInt(hotelRoom) - Integer.parseInt(noOfRoomsFrag);
                postDataUsingVolley(getDetailedHotelFragment);

            }
        });
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         tv_demo = (TextView)view.findViewById(R.id.tv_demo);
    }

    public void displayReceivedData(String fullName,String phoneNumber,String Emaild,String GuestName,String noOfRooms)
    {
        fullNameFrag =fullName;
        phoneNumberFrag =phoneNumber;
        EmaildFrag =Emaild;
        GuestNameFrag =GuestName;
        noOfRoomsFrag = noOfRooms;

        tv_demo.setText("Data received: "+fullNameFrag+" " + phoneNumberFrag+" " +EmaildFrag+" " +GuestNameFrag+" "+noOfRoomsFrag);
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
    private void postDataUsingVolley(DetailedHotel getDetailedHotel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", fullNameFrag);
        params.put("email", EmaildFrag);
        params.put("phone", phoneNumberFrag);
        params.put("guestName", GuestNameFrag);
        params.put("numberOfRooms", noOfRoomsFrag);
        params.put("numberOfHotelRooms", String.valueOf(updatedRoom));
        params.put("userID",  currentUser.getUid());
        params.put("hotelID", getDetailedHotelFragment.getId());
        params.put("hotelName", getDetailedHotelFragment.getName());
        params.put("cardNumber", CardNumber);
        String url = "https://holidayhype.herokuapp.com/api/hotelBooking/insert";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject  response) {
                        Log.d("TAG", response.toString());
//                        msgResponse.setText(response.toString());
                       // hideProgressDialog();
                        Log.v("responseinsert", response.toString());
                        try {
                            String hotelBookingRefId = response.getString("_id");
                            SM2.sendBookingData(response.getString("name"),response.getString("hotelName"),hotelBookingRefId
                                    ,response.getString("email"));
                            HotelBookingActivity.viewPager.arrowScroll(View.FOCUS_RIGHT);
                            Toast.makeText(getActivity(), "Data added to API", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                             }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
//                hideProgressDialog();
                Log.v("responseinsert", error.toString());
                Toast.makeText(getActivity(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Passing some request headers
             */

        };

        // Adding request to request queue
        queue.add(jsonObjReq);
    }
}