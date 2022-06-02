package com.example.holidayhipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.holidayhipe.databinding.ActivityAccountBinding;
import com.example.holidayhipe.databinding.ActivityHomeBinding;

public class AccountActivity extends AppCompatActivity {

    private ActivityAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_home);
        this.binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.bottomNavigationView.setSelectedItemId(R.id.fourthFragment);
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
}