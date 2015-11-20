package com.siddharth.aprwalkathon;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class LocationCheckService extends IntentService implements LocationListener, GoogleApiClient.ConnectionCallbacks {

    private final String TAG = "LocationCheckService";
    private List<Track.MyPoint> turns;
    private GoogleApiClient mGoogleApiClient;

    public LocationCheckService(MyLocationListener myLocationListener) {
        super("LocationCheckService");
    }

    public LocationCheckService(){
        super("LocationCheckService");
    }

    public static Location location;

    private LocationRequest createLocationRequest(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

        }
    }

    private void buildGoogleAPIClient(){

        Log.d(TAG, "Building Google Api Client");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
        Log.d(TAG, "Successfully built Google Api Client");
        mGoogleApiClient.connect();

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected(Bundle bundle) {

        Log.d(TAG, "Connected!");
        turns = new ArrayList<Track.MyPoint>();
        for(Track.MyPoint p : Track.points){
            if(p.turn != 0)
                turns.add(p);
        }

        Log.d(TAG, "Requesting Location updates...");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, createLocationRequest(), this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void removeLocationUpdates(){
        Log.d(TAG, "Removing Location updates...");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Log.d(TAG, "Removed updates.");
    }
}
