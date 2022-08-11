package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.gbc.holidayhype.adapter.SearchAdapter;
import com.gbc.holidayhype.databinding.ActivitySearchBinding;
import com.gbc.holidayhype.model.AttractionData;
import com.gbc.holidayhype.model.RecentsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySearchBinding binding;
    RecyclerView recentRecycler, topPlacesRecycler;

    SearchAdapter searchAdapter;

    private RequestQueue requestQueue;
    private ArrayList<AttractionData> allplacesList = new ArrayList<AttractionData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_search);
        this.binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.goBack.setOnClickListener(this::onClick);
        binding.searchView.clearFocus();
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });


//        recentDataList.add(new RecentsData("Calgary", "Canada", "From $200", R.drawable.calgary));
//        recentDataList.add(new RecentsData("Montreal", "Canada", "From $300", R.drawable.montreal));
//        recentDataList.add(new RecentsData("Niagara Fall", "Canada", "From $200", R.drawable.niagarafalls));
//        recentDataList.add(new RecentsData("Toronto", "Canada", "From $300", R.drawable.toronto));
//        recentDataList.add(new RecentsData("Vancouver", "Canada", "From $200", R.drawable.vancouver));
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        fetchAttractionPlaces();

        setRecentRecycler(allplacesList);


    }

    private void setRecentRecycler(ArrayList<AttractionData> recentDataList) {

        recentRecycler = findViewById(R.id.search_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        searchAdapter = new SearchAdapter(this, recentDataList);
        recentRecycler.setAdapter(searchAdapter);

    }

    private void filterList(String text){
        List<AttractionData> filteredList = new ArrayList<>();
        for (AttractionData data : allplacesList){
            if (data.getPlaceName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(data);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this,"no data found", Toast.LENGTH_SHORT).show();
        }else {
            searchAdapter.setFilteredList(filteredList);
        }
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.go_Back: {
                    finish();
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

                        Log.e("TAG", "onResponse: "+response );
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


                                allplacesList.add(data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            MovieAdapter adapter = new MovieAdapter(MainActivity.this , movieList);
//
                            setRecentRecycler(allplacesList);

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(jsonArrayRequest);
    }
}