package com.example.holidayhipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.holidayhipe.adapter.RecentsAdapter;
import com.example.holidayhipe.adapter.TopPlacesAdapter;
import com.example.holidayhipe.databinding.ActivityMainBinding;
import com.example.holidayhipe.model.RecentsData;
import com.example.holidayhipe.model.TopPlacesData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_main);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());



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

                    break;
            }
            return true;
        });
    }



}
