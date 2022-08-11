package com.gbc.holidayhype.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FlightsData implements Parcelable {
    String id;
    String flightName;
    String from;
    String to;
    String departureTiming;
    String landingTiming;
    String totalTiming;
    String fare;
    String type;
    String imageUrl;

    public FlightsData(){

    }

    public FlightsData(String id, String flightName, String from, String to, String departureTiming, String landingTiming, String totalTiming, String fare, String type, String imageUrl) {
        this.id = id;
        this.flightName = flightName;
        this.from = from;
        this.to = to;
        this.departureTiming = departureTiming;
        this.landingTiming = landingTiming;
        this.totalTiming = totalTiming;
        this.fare = fare;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    protected FlightsData(Parcel in) {
        id = in.readString();
        flightName = in.readString();
        from = in.readString();
        to = in.readString();
        departureTiming = in.readString();
        landingTiming = in.readString();
        totalTiming = in.readString();
        fare = in.readString();
        type = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<FlightsData> CREATOR = new Creator<FlightsData>() {
        @Override
        public FlightsData createFromParcel(Parcel in) {
            return new FlightsData(in);
        }

        @Override
        public FlightsData[] newArray(int size) {
            return new FlightsData[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartureTiming() {
        return departureTiming;
    }

    public void setDepartureTiming(String departureTiming) {
        this.departureTiming = departureTiming;
    }

    public String getLandingTiming() {
        return landingTiming;
    }

    public void setLandingTiming(String landingTiming) {
        this.landingTiming = landingTiming;
    }

    public String getTotalTiming() {
        return totalTiming;
    }

    public void setTotalTiming(String totalTiming) {
        this.totalTiming = totalTiming;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(getId());
        dest.writeString(getFlightName());
        dest.writeString(getFrom());
        dest.writeString(getTo());
        dest.writeString(getDepartureTiming());
        dest.writeString(getLandingTiming());
        dest.writeString(getTotalTiming());
        dest.writeString(getFare());
        dest.writeString(getType());
        dest.writeString(getImageUrl());
    }
}