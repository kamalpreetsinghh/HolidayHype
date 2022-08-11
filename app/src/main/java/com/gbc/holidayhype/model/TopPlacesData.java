package com.gbc.holidayhype.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TopPlacesData implements Parcelable {

    String placeName;
    String countryName;
    String price;
    Integer imageUrl;

    protected TopPlacesData(Parcel in) {
        placeName = in.readString();
        countryName = in.readString();
        price = in.readString();
        imageUrl = in.readInt();
    }

    public static final Creator<TopPlacesData> CREATOR = new Creator<TopPlacesData>() {
        @Override
        public TopPlacesData createFromParcel(Parcel in) {
            return new TopPlacesData(in);
        }

        @Override
        public TopPlacesData[] newArray(int size) {
            return new TopPlacesData[size];
        }
    };

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    public TopPlacesData(String placeName, String countryName, String price, Integer imageUrl) {
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
