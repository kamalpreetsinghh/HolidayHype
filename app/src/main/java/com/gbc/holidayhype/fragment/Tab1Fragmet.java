package com.gbc.holidayhype.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.gbc.holidayhype.HotelBookingActivity;
import com.gbc.holidayhype.R;
import com.google.android.material.textfield.TextInputEditText;

public class Tab1Fragmet extends Fragment {
    Button btn_nextPayment;
    CheckBox checkBox;
    TextInputEditText tv_guestName, tv_phoneNumber, tv_emailAddress;
    TextInputEditText tv_fullName,tv_NumOfRooms;
    SendMessage SM;
    public Tab1Fragmet() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1_fragmet, container, false);
        btn_nextPayment = view.findViewById(R.id.btn_nextPayment);
        checkBox = view.findViewById(R.id.checkBox);
        tv_fullName = view.findViewById(R.id.tv_fullName);
        tv_phoneNumber = view.findViewById(R.id.tv_phoneNumber);
        tv_emailAddress =view.findViewById(R.id.tv_emailAddress);
        tv_guestName = view.findViewById(R.id.tv_guestName);
        tv_NumOfRooms =view.findViewById(R.id.tv_NumOfRooms);

        btn_nextPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    SM.sendData(tv_fullName.getText().toString(),tv_phoneNumber.getText().toString(),tv_emailAddress.getText().toString(),tv_guestName.getText().toString(),tv_NumOfRooms.getText().toString());
                    HotelBookingActivity.viewPager.arrowScroll(View.FOCUS_RIGHT);
                }

            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (tv_fullName.getText().toString() != null) {
                    tv_guestName.setText(tv_fullName.getText().toString());
                } else {
                    Toast.makeText(getActivity(), " Please Enter Full Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
  public interface SendMessage {
        void sendData(String fullName,String phoneNumber,String Emaild,String GuestName,String noOfRooms);
    }
    private Boolean validateData() {
        Boolean validData = true;

        if (this.tv_fullName.getText().toString().isEmpty()) {
            this.tv_fullName.setError("Full Name cannot be empty");
            validData = false;
        }
        if (this.tv_phoneNumber.getText().toString().isEmpty()) {
            this.tv_phoneNumber.setError("Phone cannot be empty");
            validData = false;
        }
        if (this.tv_emailAddress.getText().toString().isEmpty()) {
            this.tv_emailAddress.setError("Email cannot be empty");
            validData = false;
        }
        if (this.tv_guestName.getText().toString().isEmpty()) {
            this.tv_guestName.setError("Guest Name cannot be empty");
            validData = false;
        }
        if (this.tv_NumOfRooms.getText().toString().isEmpty()) {
            this.tv_NumOfRooms.setError("No. of Rooms cannot be empty");
            validData = false;
        }

        return validData;
    }


}