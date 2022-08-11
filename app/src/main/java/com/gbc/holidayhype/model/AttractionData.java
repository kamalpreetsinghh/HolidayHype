package com.gbc.holidayhype.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AttractionData implements Parcelable {
    String id;
    String placeName;
    String country;
    String placeImage;
    String description;
    String visitPlace1;
    String visitPlace2;
    String visitPlace3;

    public AttractionData(String id, String placeName, String country, String placeImage, String description, String visitPlace1, String visitPlace2, String visitPlace3) {
        this.id = id;
        this.placeName = placeName;
        this.country = country;
        this.placeImage = placeImage;
        this.description = description;
        this.visitPlace1 = visitPlace1;
        this.visitPlace2 = visitPlace2;
        this.visitPlace3 = visitPlace3;
    }

    protected AttractionData(Parcel in) {
        id = in.readString();
        placeName = in.readString();
        country = in.readString();
        placeImage = in.readString();
        description = in.readString();
        visitPlace1 = in.readString();
        visitPlace2 = in.readString();
        visitPlace3 = in.readString();
    }

    public static final Creator<AttractionData> CREATOR = new Creator<AttractionData>() {
        @Override
        public AttractionData createFromParcel(Parcel in) {
            return new AttractionData(in);
        }

        @Override
        public AttractionData[] newArray(int size) {
            return new AttractionData[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisitPlace1() {
        return visitPlace1;
    }

    public void setVisitPlace1(String visitPlace1) {
        this.visitPlace1 = visitPlace1;
    }

    public String getVisitPlace2() {
        return visitPlace2;
    }

    public void setVisitPlace2(String visitPlace2) {
        this.visitPlace2 = visitPlace2;
    }

    public String getVisitPlace3() {
        return visitPlace3;
    }

    public void setVisitPlace3(String visitPlace3) {
        this.visitPlace3 = visitPlace3;
    }

    public static Creator<AttractionData> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(getId());
        dest.writeString(getCountry());
        dest.writeString(getPlaceName());
        dest.writeString(getDescription());
        dest.writeString(getPlaceImage());
        dest.writeString(getVisitPlace1());
        dest.writeString(getVisitPlace2());
        dest.writeString(getVisitPlace3());

    }
}
