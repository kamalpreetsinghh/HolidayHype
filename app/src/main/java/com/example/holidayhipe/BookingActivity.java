package com.example.holidayhipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.holidayhipe.databinding.ActivityBookingBinding;
import com.example.holidayhipe.databinding.ActivityDetailsBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityBookingBinding binding;
    private String items;
    private String className;
    MaterialDatePicker materialDatePicker;
    private static final String TAG = "MainActivity";
    String[] item = new String[]{"Calgary International Airport","Edmonton International Airport", "Fredericton International Airport", "Gander International Airport",
            "Halifax Stanfield International Airport",
            "Greater Moncton Roméo LeBlanc International Airport",
            "Montréal–Trudeau International Airport",
            "Ottawa Macdonald–Cartier International Airport",
            "Québec/Jean Lesage International Airport",
            "St. John's International Airport",
            "Toronto Pearson International Airport",
            "Vancouver International Airport",
            "Winnipeg International Airport",
};
    String[] planeClass = new String[]{"Business","Premium economy", "Economy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_booking);

        this.binding = ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        binding.imageView4.setOnClickListener(this::onClick);
        binding.roundTrip.setOnClickListener(this::onClick);
        binding.oneWayTrip.setOnClickListener(this::onClick);
        binding.selectDate.setOnClickListener(this::onClick);
        binding.selectDate2.setOnClickListener(this::onClick);

        binding.numberOfChild.setMinValue(0);
        binding.numberOfChild.setMaxValue(10);
        binding.numberOfChild.setValue(1);

        binding.numberOfAdult.setMinValue(1);
        binding.numberOfAdult.setMaxValue(10);
        binding.numberOfAdult.setValue(1);

        long today = MaterialDatePicker.todayInUtcMilliseconds();

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a data");
        builder.setSelection(today);
        materialDatePicker = builder.build();

        binding.spinnerFirstPlace.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,item));
        binding.spinnerFirstPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                items = binding.spinnerFirstPlace.getSelectedItem().toString();
                Log.e("error",items);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerSecondPlace.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,item));
        binding.spinnerSecondPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                items = binding.spinnerSecondPlace.getSelectedItem().toString();
                Log.e("error",items);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.classSelect.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,planeClass));
        binding.classSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                className = binding.classSelect.getSelectedItem().toString();
                Log.e("error",items);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.imageView4: {
                    finish();
                    break;
                }
                case R.id.roundTrip: {
                    binding.roundTrip.setBackgroundColor(Color.rgb(104,227,28));
                    binding.oneWayTrip.setBackgroundColor(Color.DKGRAY);
                    break;
                }
                case R.id.oneWayTrip: {
                    binding.oneWayTrip.setBackgroundColor(Color.rgb(104,227,28));
                    binding.roundTrip.setBackgroundColor(Color.DKGRAY);
                    break;
                }
                case R.id.selectDate:
                case R.id.selectDate2: {
                    materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                    break;
                }
            }
        }
    }
}