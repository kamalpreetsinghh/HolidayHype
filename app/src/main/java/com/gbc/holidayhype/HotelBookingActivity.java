package com.gbc.holidayhype;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.gbc.holidayhype.adapter.TabLayoutAdapter;
import com.gbc.holidayhype.fragment.Tab1Fragmet;
import com.gbc.holidayhype.fragment.Tab2Fragmet;
import com.gbc.holidayhype.fragment.Tab3Fragmet;
import com.gbc.holidayhype.model.DetailedHotel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HotelBookingActivity extends AppCompatActivity implements Tab1Fragmet.SendMessage , Tab2Fragmet.SendMessagetab2{
    TabLayout tabLayout;
    public static ViewPager viewPager;
    DetailedHotel getDetailedHotel;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hotel_booking);
        getDetailedHotel = (DetailedHotel) getIntent().getSerializableExtra("detailData");

        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Guest Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Payment"));
        tabLayout.addTab(tabLayout.newTab().setText("Success"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabLayoutAdapter adapter=new TabLayoutAdapter(this,getSupportFragmentManager(),tabLayout.getTabCount(),getDetailedHotel);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener((ViewPager.OnPageChangeListener) new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // No user is signed in
            Log.v("UserId","not found");
        } else {
            Log.v("UserId",currentUser.getUid());
        }
    }
    @Override
    public void sendData(String fullName,String phoneNumber,String Emaild,String GuestName,String noOfRooms) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + 1;
        Tab2Fragmet f = (Tab2Fragmet) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(fullName,phoneNumber,Emaild,GuestName,noOfRooms);
    }
    @Override
    public void sendBookingData(String Name,String HotelName,String HotelBookingId,String Emaild) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + 2;
        Tab3Fragmet f = (Tab3Fragmet) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedBookingData(Name,HotelName,HotelBookingId,Emaild);
    }
}