package com.gbc.holidayhype.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gbc.holidayhype.fragment.Tab1Fragmet;
import com.gbc.holidayhype.fragment.Tab2Fragmet;
import com.gbc.holidayhype.fragment.Tab3Fragmet;
import com.gbc.holidayhype.model.DetailedHotel;

public class TabLayoutAdapter extends FragmentPagerAdapter {
 
    Context mContext;
    int mTotalTabs;
    DetailedHotel getDetailedHotelAdapter;
 
    public TabLayoutAdapter(Context context, FragmentManager fragmentManager, int totalTabs, DetailedHotel getDetailedHotel) {
        super(fragmentManager);
        mContext = context;
        mTotalTabs = totalTabs;
        getDetailedHotelAdapter = getDetailedHotel;
    }
 
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("asasas" , position + "");
        switch (position) {
            case 0:
                return new Tab1Fragmet();
            case 1:
                return new Tab2Fragmet(getDetailedHotelAdapter);
            case 2:
                return new Tab3Fragmet();
            default:
                return null;
 
        }
    }
 
    @Override
    public int getCount() {
        return mTotalTabs;
    }
}