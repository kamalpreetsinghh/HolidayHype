package com.example.holidayhipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.holidayhipe.adapter.RecentsAdapter;
import com.example.holidayhipe.adapter.TopPlacesAdapter;
import com.example.holidayhipe.databinding.ActivityHomeBinding;
import com.example.holidayhipe.databinding.ActivityMainBinding;
import com.example.holidayhipe.model.RecentsData;
import com.example.holidayhipe.model.TopPlacesData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityHomeBinding binding;

    RecyclerView recentRecycler, topPlacesRecycler;
    RecentsAdapter recentsAdapter;
    TopPlacesAdapter topPlacesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_home);
        this.binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.textView4.setOnClickListener(this::onClick);

         ArrayList<RecentsData> recentDataList = new ArrayList<>();
        recentDataList.add(new RecentsData("Calgary", "Canada", "From $200", R.drawable.calgary));
        recentDataList.add(new RecentsData("Montreal", "Canada", "From $300", R.drawable.montreal));
        recentDataList.add(new RecentsData("Niagara Fall", "Canada", "From $200", R.drawable.niagarafalls));
        recentDataList.add(new RecentsData("Toronto", "Canada", "From $300", R.drawable.toronto));
        recentDataList.add(new RecentsData("Vancouver", "Canada", "From $200", R.drawable.vancouver));


        setRecentRecycler(recentDataList);

        List<TopPlacesData> topPlacesDataList = new ArrayList<>();
        topPlacesDataList.add(new TopPlacesData("CN Tower", "Toronto", "$100 - $200", R.drawable.toronto));
        topPlacesDataList.add(new TopPlacesData("Royal Ontario Museum", "Toronto", "$200 - $500", R.drawable.museum));
        topPlacesDataList.add(new TopPlacesData("Ripley's Aquarium", "Toronto", "$200 - $500", R.drawable.aquarium));
        topPlacesDataList.add(new TopPlacesData("Art Gallery of Ontario", "Toronto", "$200 - $500", R.drawable.artgallery));
        topPlacesDataList.add(new TopPlacesData("St. Lawrence Market", "Toronto", "$200 - $500", R.drawable.stlawrence));

        setTopPlacesRecycler(topPlacesDataList);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.firstFragment:
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.secondFragment:

                    break;
                case R.id.thirdFragment:

                    break;
                case R.id.fourthFragment:
                    startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return true;
        });

    }

        private void setRecentRecycler(ArrayList<RecentsData> recentDataList) {

        recentRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, recentDataList);
        recentRecycler.setAdapter(recentsAdapter);

    }

    private void setTopPlacesRecycler(List<TopPlacesData> topPlacesDataList) {

        topPlacesRecycler = findViewById(R.id.top_places_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        topPlacesRecycler.setLayoutManager(layoutManager);
        topPlacesAdapter = new TopPlacesAdapter(this, topPlacesDataList);
        topPlacesRecycler.setAdapter(topPlacesAdapter);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.textView4: {
                    startActivity(new Intent(getApplicationContext(),ShowAllPlaces.class));
                }
            }
        }
    }
}