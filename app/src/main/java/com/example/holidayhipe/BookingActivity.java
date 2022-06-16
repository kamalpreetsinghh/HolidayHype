package com.example.holidayhipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.holidayhipe.databinding.ActivityBookingBinding;
import com.example.holidayhipe.databinding.ActivityDetailsBinding;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityBookingBinding binding;
    private String items;
    private String className;
    private String tripFlag = "1";
    MaterialDatePicker materialDatePicker;
    MaterialDatePicker materialDatePicker2;
    private static final String TAG = "MainActivity";
    String[] item = new String[]{"Calgary","Alberta", "Toronto", "Vancouver", "Montreal", "Ottawa ", "Quebec",
            "St. John's",
            "Winnipeg", "Lincoln", "Gander",
            "Halifax",
            "Dieppe"
};
    ArrayList<String> allItems = new ArrayList<>();
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
        binding.searchFlightButton.setOnClickListener(this::onClick);

        binding.numberOfChild.setMinValue(0);
        binding.numberOfChild.setMaxValue(10);
        binding.numberOfChild.setValue(1);

        binding.numberOfAdult.setMinValue(1);
        binding.numberOfAdult.setMaxValue(10);
        binding.numberOfAdult.setValue(1);

        Intent intent = getIntent();
        String des = intent.getStringExtra("destination");
        String[] item2 = new String[]{des};

        for (int i=0;i<item.length;i++){
            if (item[i].equals(des)){
                Log.d(TAG, "onCreate: Same places");
            }else {
                allItems.add(item[i]);

            }
        }



        long today = MaterialDatePicker.todayInUtcMilliseconds();

        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a data");
        builder.setSelection(today);
        builder.setCalendarConstraints(constraintBuilder.build());
        materialDatePicker = builder.build();
        materialDatePicker2 = builder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Log.d(TAG, "onPositiveButtonClick: "+materialDatePicker.getHeaderText());
                binding.selectDate.setText(materialDatePicker.getHeaderText());
            }
        });

        materialDatePicker2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Log.d(TAG, "onPositiveButtonClick: "+materialDatePicker2.getHeaderText());
                binding.selectDate2.setText(materialDatePicker2.getHeaderText());
            }
        });

        binding.spinnerFirstPlace.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,allItems));
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

        binding.spinnerSecondPlace.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,item2));
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
                    binding.selectDate2.setEnabled(true);
                    tripFlag = "1";
                    break;
                }
                case R.id.oneWayTrip: {
                    binding.oneWayTrip.setBackgroundColor(Color.rgb(104,227,28));
                    binding.roundTrip.setBackgroundColor(Color.DKGRAY);
                    binding.selectDate2.setEnabled(false);
                    tripFlag = "2";
                    break;
                }
                case R.id.selectDate:{
                    materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                    break;
                }
                case R.id.selectDate2: {
                    materialDatePicker2.show(getSupportFragmentManager(), "DATE_PICKER");
                    break;
                }
                case R.id.searchFlightButton: {


                    String val1 = binding.selectDate.getText().toString().toUpperCase();
                    String val2 = binding.selectDate2.getText().toString().toUpperCase();
                    Log.d(TAG, "onClick: "+val1);

                    if (tripFlag.equals("1")){
                        if (val1.equals("SELECT DATE") || val2.equals("SELECT DATE")){
                            Toast.makeText(getApplicationContext(),
                                    "Please select the date!",
                                    Toast.LENGTH_SHORT).show();

                        }else{
                            startActivityFunc();
                        }

                    }
                    else {
                        if (val1.equals("SELECT DATE")){
                            Toast.makeText(getApplicationContext(),
                                    "Please select the date!",
                                    Toast.LENGTH_SHORT).show();

                        }else{
                            startActivityFunc();
                        }

                    }


                    break;
                }
            }
        }
    }

    public void startActivityFunc(){
        String firstPlace = binding.spinnerFirstPlace.getSelectedItem().toString();
        String secondPlace = binding.spinnerSecondPlace.getSelectedItem().toString();
        Intent i = new Intent(this, FlightsActivity.class);
        i.putExtra("from",firstPlace);
        i.putExtra("to",secondPlace);
        startActivity(i);
    }
}