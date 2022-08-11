package com.gbc.holidayhype;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gbc.holidayhype.model.FlightApiData;
import com.gbc.holidayhype.model.FlightsData;
import com.gbc.holidayhype.model.User;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FlightApi {

    volleyFlightInterface mResultCallback = null;
    volleyFlightInterface2 mResultCallback2 = null;
    Context mContext;

    FlightApi(volleyFlightInterface resultCallback, Context context){
        mResultCallback = resultCallback;
        mContext = context;
    }

    FlightApi(volleyFlightInterface2 resultCallback, Context context,String unknown){
        mResultCallback2 = resultCallback;
        mContext = context;
    }
    public FlightApi() {

    }

    FlightApiData flightDetails = new FlightApiData();

    private final String TAG = this.getClass().getCanonicalName();

    public void addFlight(Context context, FlightApiData data) {


        final JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("flightID",data.getFlightID());
            jsonObject.put("userID", data.getUserID());
            jsonObject.put("flightTo",data.getFlightTo());
            jsonObject.put("flightFrom",data.getFlightFrom());
            jsonObject.put("type",data.getType());
            jsonObject.put("departureDate", data.getDepartureDate());
            jsonObject.put("landingDate",data.getLandingDate());
            jsonObject.put("className", data.getClassName());
            jsonObject.put("numberOfChildren", data.getNumberOfChildren());
            jsonObject.put("numberOfAdults", data.getNumberOfAdults());

        }
        catch (JSONException e) {
            // handle exception
        }

        String url = "https://holidayhype.herokuapp.com/api/flightBooking/insert";

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>(){
            public void onResponse(JSONObject response) {

//                loadingPB.setVisibility(View.GONE);
                Log.e("Data added to api", response.toString());

                Toast.makeText(context, "Data added to API", Toast.LENGTH_SHORT).show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.v("Data not added to api", error.toString());
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();


                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public void fetchFlight(final String requestType,Context context) {

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://holidayhype.herokuapp.com/api/flightBooking/user/"+id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        mResultCallback.notifySuccess(requestType,response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.getMessage() );

            }
        });

        queue.add(jsonArrayRequest);

    }

    public void fetchSpecificFlight(final String requestType,Context context, String flightId) {


        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://holidayhype.herokuapp.com/api/flightBooking/"+flightId;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        mResultCallback2.notifySuccess(requestType,response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.getMessage() );

            }
        });

        queue.add(jsonArrayRequest);

    }


    public void deleteFlight(Context context, String bookingId) {


        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://holidayhype.herokuapp.com/api/flightBooking/"+bookingId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, "onResponse: Data deleted properly" );

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.getMessage() );

            }
        });

        queue.add(jsonObjectRequest);

    }

//
//    public void updateUser(Context context,User user) {
//
//        String url = "https://holidayhype.herokuapp.com/api/user/"+user.getUserID();
//
//        RequestQueue queue = Volley.newRequestQueue(context);
//        StringRequest request = new StringRequest(Request.Method.PUT, url, new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
////                loadingPB.setVisibility(View.GONE);
//                Log.e("Data added to api", response.toString());
//
//                Toast.makeText(context, "Data added to API", Toast.LENGTH_SHORT).show();
//
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // method to handle errors.
//                Log.v("Data not added to api", error.toString());
//                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // below line we are creating a map for
//                // storing our values in key and value pair.
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("userID",user.getUserID());
//                params.put("firstName", user.getFirstName());
//                params.put("lastName",user.getLastName());
//                params.put("email", user.getEmail());
//                params.put("password", user.getPassword());
//
//                // at last we are
//                // returning our params.
//                return params;
//            }
//        };
//        // below line is to make
//        // a json object request.
//        queue.add(request);
//    }

}
