package com.gbc.holidayhype.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RecentsData implements Parcelable {

    String placeName;
    String countryName;
    String price;
    Integer imageUrl;

    protected RecentsData(Parcel in) {
        placeName = in.readString();
        countryName = in.readString();
        price = in.readString();
        imageUrl = in.readInt();
//        if (in.readByte() == 0) {
//            imageUrl = null;
//        } else {
//            imageUrl = in.readInt();
//        }
    }

    public static final Creator<RecentsData> CREATOR = new Creator<RecentsData>() {
        @Override
        public RecentsData createFromParcel(Parcel in) {
            return new RecentsData(in);
        }

        @Override
        public RecentsData[] newArray(int size) {
            return new RecentsData[size];
        }
    };

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RecentsData(String placeName, String countryName, String price, Integer imageUrl) {
        this.placeName = placeName;
        this.countryName = countryName;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(getPlaceName());
        dest.writeString(getCountryName());
        dest.writeString(getPrice());
        dest.writeInt(getImageUrl());

    }
}
