package com.siddharth.aprwalkathon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.CameraUpdateFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Track extends AppCompatActivity{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    static List<MyPoint> points;
    static List<MyPoint> turns;
    GoogleApiClient mGoogleApiClient;
    private String TAG = "LocationListener";
    static boolean follow = false;
    String JSONString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        setUpMapIfNeeded();
    }

    @Override
    protected void onNewIntent(Intent intent){
        Log.d(TAG, "New Intent");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_track, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exitTrack) {
            //remove location updates
            Intent stopService = new Intent(this, LocationService.class);
            stopService(stopService);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        JSONString = getIntent().getStringExtra(MainActivity.TRACK_JSON);
        Track.points = readJSONTurns(JSONString);
        Track.turns = makeTurns();
        drawPoints(Track.points);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Track.follow = !Track.follow;
                return true;
            }
        });
        /*mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Track.follow = false;
            }
        });*/
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Track.points.get(0).latitude, Track.points.get(0).longitude), 18));

        Intent intent = new Intent(this, LocationService.class);
        intent.putExtra(MainActivity.TRACK_JSON, JSONString);
        startService(intent);

    }

    private ArrayList<MyPoint> readJSONTurns(String json){

        JSONObject file;
        JSONArray points;

        try {
            file = new JSONObject(json);
            points = file.getJSONArray("snappedPoints");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<MyPoint> to_return = new ArrayList<MyPoint>();

        for(int i = 0; i < points.length(); i++){

            try {
                JSONObject point = points.getJSONObject(i);
                JSONObject location = point.getJSONObject("location");
                MyPoint to_add = new MyPoint();
                to_add.latitude = location.getDouble("latitude");
                to_add.longitude = location.getDouble("longitude");
                to_add.turn = point.getInt("turn");
                to_return.add(to_add);
            } catch (JSONException e) {
                continue;
            }

        }

        return to_return;

    }

    private ArrayList<MyPoint> makeTurns(){
        ArrayList<MyPoint> turn = new ArrayList<>();
        for(MyPoint point : points){
            if(point.turn != 0){
                turn.add(point);
            }
        }
        return turn;
    }

    private Polyline drawPoints(List<MyPoint> points){

        PolylineOptions draw = new PolylineOptions();
        draw.geodesic(true);
        for (int i = 0; i < points.size(); i++){

            draw.add(new LatLng(points.get(i).latitude, points.get(i).longitude));

        }
        draw.color(Color.BLUE);

        return mMap.addPolyline(draw);

    }

    public class MyPoint{double latitude, longitude; int turn;}

}
