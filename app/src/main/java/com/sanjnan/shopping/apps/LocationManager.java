package com.sanjnan.shopping.apps;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationManager
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public interface Listener {
        public void initializationComplete();
    }

    private GoogleApiClient googleApiClient;
    private Locale locale;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private Address lastAddress;
    private Listener listener;
    private final AbstractActivity context;

    private boolean locationConnected = false;

    private boolean locationServiceAvailable = false;

    private final Logger logger = Logger.getLogger(LocationManager.class.getName());

    private static LocationManager INSTANCE;

    public static synchronized void initialize(AbstractActivity context,
                                               Locale locale) {
        INSTANCE = new LocationManager(context, locale);
        INSTANCE.listener = context;
    }

    public static LocationManager getInstance() {
        return INSTANCE;
    }

    private LocationManager(AbstractActivity context, Locale locale) {
        this.context = context;
        this.locale = locale;
        googleApiClient = new GoogleApiClient
                .Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public static boolean isLocationAvailable() {
        return INSTANCE != null && INSTANCE.locationConnected && INSTANCE.lastLocation != null;
    }

    private Location getLastLocation() {
        return lastLocation;
    }
    public void pause() {

        if (googleApiClient != null && googleApiClient.isConnected()) {

            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }
    private void updateLastAddress() {

        if (lastLocation != null) {

            Geocoder geocoder = new Geocoder(context, locale);
            try {
                List<Address> la = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 5);
                if (la.size() > 0) {
                    lastAddress = la.get(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void startLocationUpdates() {
        try {

            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            updateLastAddress();
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
        catch(SecurityException sex) {
            logger.log(Level.SEVERE, "Location update failed because of permissions.", sex);
        }
        if (listener != null) {
            listener.initializationComplete();
        }
    }
    @Override
    public void onConnectionSuspended(int i) {

        locationConnected = false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        context.showExitDialog(ExitReason.LOCATION_SERVICE_FAILED);
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LocationManager.getInstance().startLocationUpdates();
        locationConnected = true;
    }
    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        updateLastAddress();
    }

    public Double getLastLatitude() {
        return lastLocation == null ? 0.0 : lastLocation.getLatitude();
    }
    public Double getLastLongitude() {
        return lastLocation == null ? 0.0 : lastLocation.getLongitude();
    }
    public States getLastState() {
        if (lastAddress != null) {
            String state = lastAddress.getAdminArea();
            if (state != null) {
                try {

                    return States.create(state);
                }
                catch (IllegalArgumentException e) {
                    return States.MP;
                }
            }
        }
        return States.MP;
    }
    public String getLastCity() {
        if (lastAddress != null) {
            String city = lastAddress.getLocality();
            if (city == null) {
                city = lastAddress.getSubAdminArea();
            }
            if (city != null) {
                try {

                    return city;
                }
                catch (IllegalArgumentException e) {
                }
            }
        }
        return null;
    }
    public void setListener(Listener listener) {
        this.listener = listener;
    }
}