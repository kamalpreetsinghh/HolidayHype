package com.gbc.holidayhype.model;

public class FlightApiData2 {

    String flightID;
    String userID;
    String flightTo;
    String flightFrom;
    String type;
    String departureDate;
    String landingDate;
    String className;
    String numberOfChildren;
    String numberOfAdults;
    String flightName;
    String imageUrl;

    public FlightApiData2(String flightID, String userID, String flightTo, String flightFrom, String type, String departureDate, String landingDate, String className, String numberOfChildren, String numberOfAdults, String flightName, String imageUrl) {
        this.flightID = flightID;
        this.userID = userID;
        this.flightTo = flightTo;
        this.flightFrom = flightFrom;
        this.type = type;
        this.departureDate = departureDate;
        this.landingDate = landingDate;
        this.className = className;
        this.numberOfChildren = numberOfChildren;
        this.numberOfAdults = numberOfAdults;
        this.flightName = flightName;
        this.imageUrl = imageUrl;
    }

    public FlightApiData2() {
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFlightTo() {
        return flightTo;
    }

    public void setFlightTo(String flightTo) {
        this.flightTo = flightTo;
    }

    public String getFlightFrom() {
        return flightFrom;
    }

    public void setFlightFrom(String flightFrom) {
        this.flightFrom = flightFrom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

