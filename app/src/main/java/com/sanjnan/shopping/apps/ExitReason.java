package com.sanjnan.shopping.apps;

/**
 * Created by vinaytrueqare on 25/10/17.
 */

public enum ExitReason {
    REQUIRED_PERMISSIONS_NOT_PRESENT("One or more of the required permissions are not present.", "Mandatory Permission Missing"),
    NO_UNIT_AND_TAX_CATEGORY_PRESENT("To add product, we need one unit and tax category.", "Add Unit and Tax Category"),
    No_PRODUCT_WITH_THIS_CODE("No product available with code %s. Please add product first.", "Add Product"),
    NO_CUSTOMER_EXISTS("Selected customer doesn't exist.", "Add Customer"),
    LOCATION_SERVICE_FAILED("Location service failed.", "Location Service Failed"),
    SIGNIN_FAILED("Sign in failed.", "Sign in Failed"),
    PLAYSTORE_SETUP_FAILED("Playstore setup failed. Device doesn't have playstore installed.", "Playstore Service Missing");
    private String reason;
    private String label;

    private ExitReason(String reason, String label) {
        this.reason = reason;
        this.label = label;
    }

    public String getReason() {
        return reason;
    }
    public String getLabel() {
        return label;
    }
}
