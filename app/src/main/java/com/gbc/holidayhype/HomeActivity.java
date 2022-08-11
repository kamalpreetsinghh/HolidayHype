package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gbc.holidayhype.R;
import com.gbc.holidayhype.adapter.RecentsAdapter;
import com.gbc.holidayhype.adapter.TopPlacesAdapter;
import com.gbc.holidayhype.databinding.ActivityHomeBinding;
import com.gbc.holidayhype.model.AttractionData;
import com.gbc.holidayhype.model.RecentsData;
import com.gbc.holidayhype.model.TopPlacesData;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityHomeBinding binding;

    RecyclerView recentRecycler, topPlacesRecycler;
    RecentsAdapter recentsAdapter;
    TopPlacesAdapter topPlacesAdapter;
    private RequestQueue requestQueue;

    ArrayList<AttractionData> placesList = new ArrayList<AttractionData>();
    ArrayList<AttractionData> recentPlacesList = new ArrayList<AttractionData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_home);
        this.binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        fetchAttractionPlaces();

        binding.textView4.setOnClickListener(this::onClick);
        binding.searchImg.setOnClickListener(this::onClick);

//         ArrayList<RecentsData> recentDataList = new ArrayList<>();
//        recentDataList.add(new RecentsData("Calgary", "Canada", "From $200", R.drawable.calgary));
//        recentDataList.add(new RecentsData("Montreal", "Canada", "From $300", R.drawable.montreal));
//        recentDataList.add(new RecentsData("Niagara Fall", "Canada", "From $200", R.drawable.niagarafalls));
//        recentDataList.add(new RecentsData("Toronto", "Canada", "From $300", R.drawable.toronto));
//        recentDataList.add(new RecentsData("Vancouver", "Canada", "From $200", R.drawable.vancouver));

//        setRecentRecycler(recentDataList);

//        List<TopPlacesData> topPlacesDataList = new ArrayList<>();
//        topPlacesDataList.add(new TopPlacesData("CN Tower", "Toronto", "$100 - $200", R.drawable.toronto));
//        topPlacesDataList.add(new TopPlacesData("Royal Ontario Museum", "Toronto", "$200 - $500", R.drawable.museum));
//        topPlacesDataList.add(new TopPlacesData("Ripley's Aquarium", "Toronto", "$200 - $500", R.drawable.aquarium));
//        topPlacesDataList.add(new TopPlacesData("Art Gallery of Ontario", "Toronto", "$200 - $500", R.drawable.artgallery));
//        topPlacesDataList.add(new TopPlacesData("St. Lawrence Market", "Toronto", "$200 - $500", R.drawable.stlawrence));

//        setTopPlacesRecycler(topPlacesDataList);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.firstFragment:
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.secondFragment:
                    startActivity(new Intent(getApplicationContext(),HotelsFragment.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.thirdFragment:
                    startActivity(new Intent(getApplicationContext(),RestaurantsFragment.class));
                    overridePendingTransition(0,0);
                    break;

                case R.id.fourthFragment:
                    startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.fifthFragment:
                    startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return true;
        });

    }

        private void setRecentRecycler(ArrayList<AttractionData> recentDataList) {

        recentRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, recentDataList);
        recentRecycler.setAdapter(recentsAdapter);

    }

    private void setTopPlacesRecycler(ArrayList<AttractionData> attractionData) {

        topPlacesRecycler = findViewById(R.id.top_places_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        topPlacesRecycler.setLayoutManager(layoutManager);
        topPlacesAdapter = new TopPlacesAdapter(this, attractionData);
        topPlacesRecycler.setAdapter(topPlacesAdapter);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.textView4: {
                    startActivity(new Intent(getApplicationContext(),ShowAllPlaces.class));
                    break;
                }
                case R.id.search_img: {
                    startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                    break;
                }
            }
        }
    }

    private void fetchAttractionPlaces() {

        String url = "https://holidayhype.herokuapp.com/api/places/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        for (int i = 0 ; i < response.length() ; i ++){

                            try {


                                JSONObject jsonObject = response.getJSONObject(i);

                                String id = jsonObject.getString("_id");
                                String placeName = jsonObject.getString("placeName");
                                String countryName = jsonObject.getString("country");
                                String image = jsonObject.getString("placeImage");
                                String desc = jsonObject.getString("description");
                                JSONArray ja_data = jsonObject.getJSONArray("placesToVisitImages");
                                int length = ja_data.length();

                                String visitPlace1 = ja_data.getString(0);
                                String visitPlace2 = ja_data.getString(1);
                                String visitPlace3 = ja_data.getString(2);




                                AttractionData data = new AttractionData(id,placeName,countryName,image,desc,visitPlace1,visitPlace2,visitPlace3);

                                if (i%3 == 0){
                                    recentPlacesList.add(data);
                                }
                                placesList.add(data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            MovieAdapter adapter = new MovieAdapter(MainActivity.this , movieList);
//
                            setRecentRecycler(recentPlacesList);
                            setTopPlacesRecycler(placesList);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(jsonArrayRequest);
    }
}
