package com.siddharth.aprwalkathon;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;
import android.location.LocationListener;

public class LocationService extends Service {

    public static final String BROADCAST_ACTION = "Hello World";
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public LocationManager locationManager;
    public MyLocationListener listener;
    private String TAG = "LocationService";
    public Location previousBestLocation = null;
    public static boolean running = false;

    //static variables to be fetched from other programs
    public static float speed;

    Intent intent;
    int counter = 0;

    @Override
    public void onCreate()
    {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        listener = new MyLocationListener(this.getBaseContext(), intent.getStringExtra(MainActivity.TRACK_JSON));
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
        running = true;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
        Log.v(TAG, "STOP_SERVICE");
        locationManager.removeUpdates(listener);
        running = false;
    }

    public class MyLocationListener implements LocationListener
    {

        Context context;
        String JSONString;

        public MyLocationListener(Context context, String JSONString){
            this.context = context;
            this.JSONString = JSONString;
        }

        public void onLocationChanged(final Location location)
        {
            Log.i(TAG, "Location changed");
            if(isBetterLocation(location, previousBestLocation)) {
                checkTurn(location);
                calculateSpeed(location);
            }
        }

        public void onProviderDisabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
        }


        public void onProviderEnabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }


        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

        private void calculateSpeed(Location location){



        }

        int count = 0;
        final int TURN_DISTANCE = 100; //TODO:change to 100!!!!

        private void checkTurn(Location location){

            if(count >= Track.turns.size()) {
                removeLocationUpdates();
                return;
            }

            //check for distance
            Location p = new Location("null");
            p.setLatitude(Track.turns.get(count).latitude);
            p.setLongitude(Track.turns.get(count).longitude);

            float distance = p.distanceTo(location);
            Log.d(TAG, Float.toString(distance));

            if (p.distanceTo(location) < TURN_DISTANCE) {

                String text = "";
                switch (Track.turns.get(count).turn) {
                    case -1:
                        text = "Take a left turn";
                        break;
                    case 1:
                        text = "Take a right turn";
                        break;
                }

                Log.d(TAG, text);

                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(1000);

                /*NOTIFICATION CODE
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("APR Walkathon")
                        .setContentText(text);
                Intent intent = new Intent(context, Track.class);
                intent.putExtra(MainActivity.TRACK_JSON, JSONString);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pIntent);
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Notification notification = builder.build();
                notification.defaults |= Notification.DEFAULT_VIBRATE;
                manager.notify(1, notification);*/

                count++;

            }

        }

        public void removeLocationUpdates(){
            Log.d(TAG, "Removing updates...");
            stopService(new Intent(context, LocationService.class));
        }

    }
}