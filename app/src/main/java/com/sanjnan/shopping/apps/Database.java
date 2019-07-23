package com.sanjnan.shopping.apps;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Currency;
import java.util.Locale;

public class Database {
    public static final Database INSTANCE = new Database();
    private final DatabaseReference databaseReference;
    private Database() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = getInstance().getReference();
    }
    public FirebaseDatabase getInstance() {
        return FirebaseDatabase.getInstance();
    }

    public void populateDummyData() {
        Currency currency = Currency.getInstance(Locale.getDefault());
        String symbol = currency.getSymbol();
        DatabaseReference vendorsReference = databaseReference.child(Constants.VENDORS_KEY).push();
        DatabaseReference productsReference = databaseReference.child(Constants.PRODUCTS_KEY);
        DatabaseReference productVendorReference = databaseReference.child(Constants.VENDOR_FOR_PRODUCTS_KEY);
        Product p = new Product("4548736089815", "https://firebasestorage.googleapis.com/v0/b/road-buddy-3a3f4.appspot.com/o/logos%2F71JWip4ygQL._AC_UL436_SEARCH212385_.jpg?alt=media&token=242c1ea5-a1fb-4486-ac0b-b4b55443a0de", "Sony WH-XB700 Wireless Extra Bass Headphones (Blue)", "Headphones made for electronic dance music (EDM)\n" + "Wireless Extra Bass Headphones for deep and punchy bass\n" +
                "Quick charge for 10min charge for 90 min play back\n" +
                "Battery life up to 30hrs for long listening hours\n" +
                "Sony | Headphones Connect APP for Android /iOS to control sound settings\n" +
                "Alexa and Google Assistant compatible with Voice Assistant feature\n" +
                "Light Weight - 195g\n" +
                "30mm Driver Unit for Clear and Powerful Sound, tollfree number 1800-103-7799", 7858.00f, symbol);
        productsReference.child(p.getId()).setValue(p);
        {

            Vendor v = new Vendor(vendorsReference.getKey(), "https://firebasestorage.googleapis.com/v0/b/road-buddy-3a3f4.appspot.com/o/logos%2Ficons8-diamond-100.png?alt=media&token=65814873-be5a-425f-9b13-75bc6f9f0796", "Cromz", 5.0f,  12.9431293,77.6956571);
            vendorsReference.setValue(v);
            VendorProduct vendorProduct = new VendorProduct(p.getId(), v.getId(), 5999.00f, 0);
            productVendorReference.child(p.getId()).child(v.getId()).setValue(vendorProduct);
        }
        {

            Vendor v = new Vendor(vendorsReference.getKey(), "https://firebasestorage.googleapis.com/v0/b/road-buddy-3a3f4.appspot.com/o/logos%2Ficons8-diamond-100.png?alt=media&token=65814873-be5a-425f-9b13-75bc6f9f0796", "MyKart", 4.5f,  12.9705219,77.6388624);
            vendorsReference.setValue(v);
            VendorProduct vendorProduct = new VendorProduct(p.getId(), v.getId(), 5699.00f, 0);
            productVendorReference.child(p.getId()).child(v.getId()).setValue(vendorProduct);
        }
        {

            Vendor v = new Vendor(vendorsReference.getKey(), "https://firebasestorage.googleapis.com/v0/b/road-buddy-3a3f4.appspot.com/o/logos%2Ficons8-diamond-100.png?alt=media&token=65814873-be5a-425f-9b13-75bc6f9f0796", "Reliant", 4.0f,  12.916597,77.5891083);
            vendorsReference.setValue(v);
            VendorProduct vendorProduct = new VendorProduct(p.getId(), v.getId(), 6299.00f, 0);
            productVendorReference.child(p.getId()).child(v.getId()).setValue(vendorProduct);
        }
    }
}
