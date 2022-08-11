package com.gbc.holidayhype.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BookingData implements Parcelable {

    String departurePlace;
    String landingPlace;
    String departureDate;
    String landingDate;
    String className;
    String tripType;
    String numberOfChildren;
    String numberOfAdults;

    public BookingData(String departurePlace, String landingPlace, String departureDate, String landingDate, String className, String tripType, String numberOfChildren, String numberOfAdults) {
        this.departurePlace = departurePlace;
        this.landingPlace = landingPlace;
        this.departureDate = departureDate;
        this.landingDate = landingDate;
        this.className = className;
        this.tripType = tripType;
        this.numberOfChildren = numberOfChildren;
        this.numberOfAdults = numberOfAdults;
    }

    protected BookingData(Parcel in) {
        departurePlace = in.readString();
        landingPlace = in.readString();
        departureDate = in.readString();
        landingDate = in.readString();
        className = in.readString();
        tripType = in.readString();
        numberOfChildren = in.readString();
        numberOfAdults = in.readString();
    }

    public static final Creator<BookingData> CREATOR = new Creator<BookingData>() {
        @Override
        public BookingData createFromParcel(Parcel in) {
            return new BookingData(in);
        }

        @Override
        public BookingData[] newArray(int size) {
            return new BookingData[size];
        }
    };

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getLandingPlace() {
        return landingPlace;
    }

    public void setLandingPlace(String landingPlace) {
        this.landingPlace = landingPlace;
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

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
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
        dest.writeString(getDeparturePlace());
        dest.writeString(getLandingPlace());
        dest.writeString(getDepartureDate());
        dest.writeString(getLandingDate());
        dest.writeString(getClassName());
        dest.writeString(getTripType());
        dest.writeString(getNumberOfChildren());
        dest.writeString(getNumberOfAdults());
    }
}
