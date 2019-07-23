package com.sanjnan.shopping.apps;

public class Vendor {
    public Vendor(String id, String url, String name, float rating, double latitude, double longitude) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Vendor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private String id;
    private String url;
    private String name;
    private float rating;
    private double latitude;
    private double longitude;
}
