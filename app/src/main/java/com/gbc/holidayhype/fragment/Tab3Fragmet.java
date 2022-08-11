package com.gbc.holidayhype.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.gbc.holidayhype.HotelsFragment;
import com.gbc.holidayhype.R;

public class Tab3Fragmet extends Fragment {

    TextView tv_HotelName,tv_Name,tv_bookingText;
    ImageView home;

    public Tab3Fragmet() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3_fragmet, container, false);
        tv_HotelName = view.findViewById(R.id.tv_HotelName);
        tv_Name = view.findViewById(R.id.tv_Name);
        tv_bookingText = view.findViewById(R.id.tv_bookingText);
        home =view.findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity();
            }
        });
//        nextActivity();
        return view;
    }
    public void displayReceivedBookingData(String Name,String HotelName,String HotelBookingId,String Emaild)
    {
        tv_Name.setText("Name: "+Name);
        tv_HotelName.setText("Hotel Name: "+HotelName);
        tv_bookingText.setText("Booking Ref Id: "+HotelBookingId);
    }
   public void nextActivity(){
       Intent i = new Intent(getActivity(), HotelsFragment.class);
       startActivity(i);
       getActivity().finish();
   }


}

