package com.example.holidayhipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.holidayhipe.adapter.RecentsAdapter;
import com.example.holidayhipe.adapter.SeeallAdapter;
import com.example.holidayhipe.databinding.ActivityDetailsBinding;
import com.example.holidayhipe.databinding.ActivityShowAllPlacesBinding;
import com.example.holidayhipe.model.RecentsData;

import java.util.ArrayList;

public class ShowAllPlaces extends AppCompatActivity implements View.OnClickListener {

    private ActivityShowAllPlacesBinding binding;
    private static final String TAG = "MainActivity";
    RecyclerView recentRecycler, topPlacesRecycler;
    SeeallAdapter seeallAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_show_all_places);
        this.binding = ActivityShowAllPlacesBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.imageView4.setOnClickListener(this::onClick);

        ArrayList<RecentsData> recentDataList = new ArrayList<>();
        recentDataList.add(new RecentsData("Calgary", "Canada", "From $200", R.drawable.calgary));
        recentDataList.add(new RecentsData("Montreal", "Canada", "From $300", R.drawable.montreal));
        recentDataList.add(new RecentsData("Niagara Fall", "Canada", "From $200", R.drawable.niagarafalls));
        recentDataList.add(new RecentsData("Toronto", "Canada", "From $300", R.drawable.toronto));
        recentDataList.add(new RecentsData("Vancouver", "Canada", "From $200", R.drawable.vancouver));


        setRecentRecycler(recentDataList);
    }
    private void setRecentRecycler(ArrayList<RecentsData> recentDataList) {

        recentRecycler = findViewById(R.id.recent_recycler_seeAll);

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recentRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        seeallAdapter = new SeeallAdapter(this, recentDataList);
        recentRecycler.setAdapter(seeallAdapter);

    }
    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.imageView4: {
                    finish();
                    break;
                }
            }
        }
    }
}