package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.gbc.holidayhype.R;
import com.gbc.holidayhype.databinding.ActivityBookingBinding;
import com.gbc.holidayhype.model.BookingData;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityBookingBinding binding;
    private String departurePlace;
    private String landingPlace;
    private String className = "Economy";
    private String departureDate;
    private String landingDate = "Not returning";
    private String tripType = "Round Trip";
    private String numberOfChildrens = "0";
    private String numberOfAdults = "1";
    ArrayList<BookingData> bookingData = new ArrayList<BookingData>();
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
    String[] planeClass = new String[]{"Economy","Premium economy","Business"};

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
        binding.numberOfChild.setValue(0);

        Log.e(TAG, "onCreate: ===="+binding.numberOfAdult.getValue() );

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


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Log.d(TAG, "onPositiveButtonClick: "+materialDatePicker.getHeaderText());
                binding.selectDate.setText(materialDatePicker.getHeaderText());
                departureDate = materialDatePicker.getHeaderText();
                String dateString = materialDatePicker.getHeaderText();

                String day = DateFormat.format("d", new Date(dateString)).toString();
                String month = DateFormat.format("MMM", new Date(dateString)).toString();
                String year = DateFormat.format("yyyy", new Date(dateString)).toString();

                int newDay = Integer.parseInt(day) - 1;
                int selectDate = Integer.parseInt(day);
                String newDate = ""+month+" "+newDay+", "+year+"";
                String selectDate2 = ""+month+" "+selectDate+", "+year+"";

                SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
                Date date = null;
                Date selectDate3 = null;


                try {
                    date = sdf.parse(newDate);
                    selectDate3 = sdf.parse(selectDate2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long startDate = date.getTime();

                constraintBuilder.setValidator(DateValidatorPointForward.from(startDate));
                builder.setCalendarConstraints(constraintBuilder.build());
                builder.setSelection(selectDate3.getTime());
                materialDatePicker2 = builder.build();

                materialDatePicker2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Log.e(TAG, "onPositiveButtonClick second date select: "+materialDatePicker2.getHeaderText());
                        binding.selectDate2.setText(materialDatePicker2.getHeaderText());
                        landingDate = materialDatePicker2.getHeaderText();
                    }
                });
            }
        });

        binding.spinnerFirstPlace.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,allItems));
        binding.spinnerFirstPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                departurePlace = binding.spinnerFirstPlace.getSelectedItem().toString();
                Log.e("spinner first item",departurePlace);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerSecondPlace.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,item2));
        binding.spinnerSecondPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                landingPlace = binding.spinnerSecondPlace.getSelectedItem().toString();
                Log.e("Spinner second item",landingPlace);
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
                Log.e("Plane class name -- ",className);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.numberOfChild.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                numberOfChildrens = String.valueOf(numberPicker.getValue());

            }
        });
        binding.numberOfAdult.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                numberOfAdults = String.valueOf(numberPicker.getValue());

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
                    tripType = "Round Trip";
                    tripFlag = "1";
                    break;
                }
                case R.id.oneWayTrip: {
                    binding.oneWayTrip.setBackgroundColor(Color.rgb(104,227,28));
                    binding.roundTrip.setBackgroundColor(Color.DKGRAY);
                    binding.selectDate2.setEnabled(false);
                    tripType = "one-way Trip";
                    tripFlag = "2";
                    break;
                }
                case R.id.selectDate:{
                    materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                    break;
                }
                case R.id.selectDate2: {
                    Log.e(TAG, "onClick: second date select");
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
        bookingData.clear();

        BookingData data = new BookingData(departurePlace,landingPlace,departureDate,landingDate,className,tripType,numberOfChildrens,numberOfAdults);

        String firstPlace = binding.spinnerFirstPlace.getSelectedItem().toString();
        String secondPlace = binding.spinnerSecondPlace.getSelectedItem().toString();
        Intent i = new Intent(this, FlightsActivity.class);
        i.putExtra("bookingArray",data);
        startActivity(i);
    }
}