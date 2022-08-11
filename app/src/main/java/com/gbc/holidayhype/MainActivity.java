package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.gbc.holidayhype.R;
import com.gbc.holidayhype.databinding.ActivityMainBinding;

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
