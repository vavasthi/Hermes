package com.sanjnan.shopping.apps;

public class Product {
    public Product(String id, String url, String title, String details, float price, String currencySymbol) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.details = details;
        this.price = price;
        this.currencySymbol = currencySymbol;
    }

    public Product() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    private String id;
    private String url;
    private String title;
    private String details;
    private float price;
    private String currencySymbol;
}
