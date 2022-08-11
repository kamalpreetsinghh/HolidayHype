package com.gbc.holidayhype;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gbc.holidayhype.adapter.RestaurantListAdaper;
import com.gbc.holidayhype.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RestaurantsFragment extends AppCompatActivity {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static RecyclerView restaurantRecycler;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Dialog dialog;
    ImageView imageViewBack, imageViewFilter;
    ArrayList<Restaurant> restaurantArrayList, restaurantTempArrayList;
    TextView textViewNoData, textViewHeader;
    SharedPreferences sharedPreferences;
    EditText editTextSearch;


    public RestaurantsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_restaurants);
        restaurantArrayList = new ArrayList<Restaurant>();
        restaurantTempArrayList = new ArrayList<Restaurant>();

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        textViewNoData = (TextView) findViewById(R.id.textViewNoData);
        textViewHeader = (TextView) findViewById(R.id.textViewHeader);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        imageViewFilter = (ImageView) findViewById(R.id.imageViewFilter);
        restaurantRecycler = (RecyclerView) findViewById(R.id.listViewRestaurant);

        dialog = new Dialog(RestaurantsFragment.this, R.style.TransparentBackground);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_loading);
        dialog.setCancelable(false);
        dialog.show();
        restaurantArrayList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://holidayhype.herokuapp.com/api/restaurants",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            Log.e("response", "Success == " + response.toString());

//                            JSONObject respObj = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject tutorialsObject = jsonArray.getJSONObject(i);
//                                Log.e("APIRes", "Success == " + tutorialsObject.getString("title"));
                                Restaurant restaurant = new Restaurant();
                                restaurant.setId(tutorialsObject.getString("_id"));
                                restaurant.setTitle(tutorialsObject.getString("title"));
                                restaurant.setDescription(tutorialsObject.getString("description"));
                                restaurant.setSrc(tutorialsObject.getString("imgSrc"));
                                restaurant.setRestaurantType(tutorialsObject.getString("restaurantType"));
                                restaurant.setLatitude(tutorialsObject.getString("latitude"));
                                restaurant.setLongitude(tutorialsObject.getString("longitude"));
                                restaurantArrayList.add(restaurant);
                                dialog.dismiss();

                                if (restaurantArrayList.size() > 0) {

                                    restaurantTempArrayList.clear();
                                    restaurantTempArrayList.addAll(restaurantArrayList);
                                    RestaurantListAdaper videoRecentAdapter = new RestaurantListAdaper(RestaurantsFragment.this, restaurantArrayList);
                                    restaurantRecycler.setLayoutManager(new LinearLayoutManager(RestaurantsFragment.this, 1, false));
                                    restaurantRecycler.setAdapter(videoRecentAdapter);
                                    textViewNoData.setVisibility(View.GONE);
                                    restaurantRecycler.setVisibility(View.VISIBLE);
                                } else {
                                    restaurantRecycler.setVisibility(View.GONE);
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


}