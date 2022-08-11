package com.gbc.holidayhype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gbc.holidayhype.databinding.ActivityPaymentBinding;
import com.gbc.holidayhype.model.BookingData;
import com.gbc.holidayhype.model.FlightApiData;
import com.gbc.holidayhype.model.FlightsData;
import com.gbc.holidayhype.repositories.FlightRepository;
import com.gbc.holidayhype.viewmodels.FlightViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityPaymentBinding binding;
    private static final String TAG = "MainActivity";
    private FlightViewModel flightViewModel;
    FlightApi flightApi = new FlightApi();



    BookingData bookingData;
    ArrayList<FlightsData> flightsData = new ArrayList<>();
    MaterialDatePicker materialDatePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

//        setContentView(R.layout.activity_home);
        this.binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.flightViewModel = FlightViewModel.getInstance(this.getApplication());
        Intent intent = getIntent();

        bookingData = intent.getParcelableExtra("bookingArray");
        flightsData = intent.getParcelableArrayListExtra("flightData");

        Log.e(TAG, "onCreate: "+flightsData );


        binding.goBackToPage2.setOnClickListener(this::onClick);
        binding.purchase.setOnClickListener(this::onClick);
        binding.selectExpiryDate.setOnClickListener(this::onClick);

        changeState(false);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_grp);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               changeState(true);
            }
        });


        long today = MaterialDatePicker.todayInUtcMilliseconds();

        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        builder.setSelection(today);
        builder.setCalendarConstraints(constraintBuilder.build());
        materialDatePicker = builder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Log.e(TAG, "onPositiveButtonClick second date select: "+materialDatePicker.getHeaderText());
                binding.selectExpiryDate.setText(materialDatePicker.getHeaderText());

            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.go_backToPage2: {
                    finish();
                    break;
                }
                case R.id.purchase: {
                        validateData();

                        break;
                }
                case R.id.selectExpiryDate: {
                    materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                    break;
                }
            }
        }
    }

    public void changeState(boolean state){
        binding.editTextEmail3.setEnabled(state);
        binding.editTextEmail2.setEnabled(state);
        binding.editTextEmail5.setEnabled(state);
    }

    private void validateData() {
        boolean validData = true;
        String cardNumber = this.binding.editTextEmail2.getText().toString();
        String cvc = this.binding.editTextEmail3.getText().toString();
        String name = this.binding.editTextEmail5.getText().toString();
        if (cardNumber.isEmpty()) {
            this.binding.editTextEmail2.setError("Card number field cannot be left empty");
            validData = false;
        }
        if (cvc.isEmpty()) {
            this.binding.editTextEmail3.setError("CVC field cannot be left empty");
            validData = false;
        }
        if (cardNumber.isEmpty()) {
            this.binding.editTextEmail5.setError("Name field cannot be left empty");
            validData = false;
        }
        String val1 = binding.selectExpiryDate.getText().toString().toUpperCase();
        if (val1.equals("EXDATE")){
            Toast.makeText(getApplicationContext(),
                    "Please select the expiry date of your card!",
                    Toast.LENGTH_SHORT).show();
            validData = false;
        }
        if (validData) {
            startActivity();
        } else {
            Toast.makeText(this, "Please enter correct inputs", Toast.LENGTH_SHORT).show();
        }
    }

    private void startActivity(){
//        flightViewModel.addItem(flightsData,bookingData);
        String flightId =  flightsData.get(0).getId();
        String userId = FirebaseAuth.getInstance().getUid();
        String flightTo = flightsData.get(0).getTo();
        String flightFrom = flightsData.get(0).getFrom();
        String type = flightsData.get(0).getType();
        String departureDate = bookingData.getDepartureDate();
        String landingDate = bookingData.getLandingDate();
        String className = bookingData.getClassName();
        String numberOfChildren = bookingData.getNumberOfChildren();
        String numberOfAdults = bookingData.getNumberOfAdults();
        FlightApiData data = new FlightApiData(flightId,userId,flightTo,flightFrom,type,departureDate,landingDate,className,numberOfChildren,numberOfAdults);


        flightApi.addFlight(PaymentActivity.this,data);
        startActivity(new Intent(getApplicationContext(),CheckoutActivity.class));
    }
}