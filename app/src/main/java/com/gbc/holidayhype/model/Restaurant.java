package com.gbc.holidayhype.model;


public class Restaurant {
    int headerImage;
    String src;
    String title;
    String restaurantType;
    String latitude;
    String longitude;
    String description;
    String id;

    public Restaurant() {
    }

    String Rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Restaurant(int headerImage, String src, String title, String restaurantType, String latitude, String longitude, String description, String nid, String Rating) {
        this.headerImage = headerImage;
        this.src = src;
        this.title = title;
        this.restaurantType = restaurantType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.id = nid;
        this.Rating = Rating;
    }

    public int getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(int headerImage) {
        this.headerImage = headerImage;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getrating() {
        return Rating;
    }

    public void setrating(String Rating) {
        this.Rating = Rating;
    }
}
