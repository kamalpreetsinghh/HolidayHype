package com.example.holidayhipe.model;

public class FlightsData {
    String flightName;
    String from;
    String to;
    String departureTiming;
    String landingTiming;
    String totalTiming;
    String date;
    String fare;
    String type;
    Integer imageUrl;

    public FlightsData(String flightName, String from, String to, String departureTiming, String landingTiming, String totalTiming, String date, String fare, String type, Integer imageUrl) {
        this.flightName = flightName;
        this.from = from;
        this.to = to;
        this.departureTiming = departureTiming;
        this.landingTiming = landingTiming;
        this.totalTiming = totalTiming;
        this.date = date;
        this.fare = fare;
        this.type = type;
        this.imageUrl = imageUrl;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}