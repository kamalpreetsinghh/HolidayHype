package com.gbc.holidayhype.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketData implements Parcelable {
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

    String departureDate;
    String landingDate;
    String className;
    String numberOfChildren;
    String numberOfAdults;

    public TicketData(){

    }

    public TicketData(String id, String flightName, String from, String to, String departureTiming, String landingTiming, String totalTiming, String fare, String type, String imageUrl, String departureDate, String landingDate, String className, String numberOfChildren, String numberOfAdults) {
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
        this.departureDate = departureDate;
        this.landingDate = landingDate;
        this.className = className;
        this.numberOfChildren = numberOfChildren;
        this.numberOfAdults = numberOfAdults;
    }

    protected TicketData(Parcel in) {
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
        departureDate = in.readString();
        landingDate = in.readString();
        className = in.readString();
        numberOfChildren = in.readString();
        numberOfAdults = in.readString();
    }

    public static final Creator<TicketData> CREATOR = new Creator<TicketData>() {
        @Override
        public TicketData createFromParcel(Parcel in) {
            return new TicketData(in);
        }

        @Override
        public TicketData[] newArray(int size) {
            return new TicketData[size];
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(String numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public String getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(String numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
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
        dest.writeString(getDepartureDate());
        dest.writeString(getLandingDate());
        dest.writeString(getClassName());
        dest.writeString(getNumberOfChildren());
        dest.writeString(getNumberOfAdults());
    }
}