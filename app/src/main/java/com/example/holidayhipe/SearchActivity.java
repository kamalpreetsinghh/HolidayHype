package com.example.holidayhipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.holidayhipe.adapter.RecentsAdapter;
import com.example.holidayhipe.adapter.SearchAdapter;
import com.example.holidayhipe.databinding.ActivityHomeBinding;
import com.example.holidayhipe.databinding.ActivitySearchBinding;
import com.example.holidayhipe.model.RecentsData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySearchBinding binding;
    RecyclerView recentRecycler, topPlacesRecycler;
    ArrayList<RecentsData> recentDataList = new ArrayList<>();
    SearchAdapter searchAdapter;

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


        recentDataList.add(new RecentsData("Calgary", "Canada", "From $200", R.drawable.calgary));
        recentDataList.add(new RecentsData("Montreal", "Canada", "From $300", R.drawable.montreal));
        recentDataList.add(new RecentsData("Niagara Fall", "Canada", "From $200", R.drawable.niagarafalls));
        recentDataList.add(new RecentsData("Toronto", "Canada", "From $300", R.drawable.toronto));
        recentDataList.add(new RecentsData("Vancouver", "Canada", "From $200", R.drawable.vancouver));


        setRecentRecycler(recentDataList);
    }

    private void setRecentRecycler(ArrayList<RecentsData> recentDataList) {

        recentRecycler = findViewById(R.id.search_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        searchAdapter = new SearchAdapter(this, recentDataList);
        recentRecycler.setAdapter(searchAdapter);

    }

    private void filterList(String text){
        List<RecentsData> filteredList = new ArrayList<>();
        for (RecentsData data : recentDataList){
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
}