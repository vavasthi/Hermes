package com.sanjnan.shopping.apps;

public class VendorProduct {
    public VendorProduct(String productId, String vendorId, float price, int availability) {
        this.productId = productId;
        this.vendorId = vendorId;
        this.price = price;
        this.availability = availability;
    }

    public VendorProduct() {
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    private String productId;
    private String vendorId;
    private float price;
    private int availability;
}
