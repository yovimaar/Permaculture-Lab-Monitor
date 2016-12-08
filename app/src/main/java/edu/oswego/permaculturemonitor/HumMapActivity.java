package edu.oswego.permaculturemonitor;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

/**
 * Created by Vi on 12/1/16.
 */

public class HumMapActivity extends FragmentActivity implements OnMapReadyCallback{
    TileProvider mProvider;
    TileOverlay mOverlay;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hum_map_layout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        DataQuery dq = new DataQuery();
        List<WeightedLatLng> weightedList ;
        List<LatLng> list;
        //populate the lists with a priming read
        weightedList = (dq.getWeightedFromZone(1, 3));////////////////WILL HAVE TO CHANGE SECOND PARAMETER BASED ON WHAT TYPE OF VALUE YOU WANT//////////////
        list = (dq.getLatLngFromZone(1, 3));////////////////WILL HAVE TO CHANGE SECOND PARAMETER BASED ON WHAT TYPE OF VALUE YOU WANT//////////////
        for(int i = 2; i < 5; i++) {
            weightedList.addAll(dq.getWeightedFromZone(i, 3));////////////////WILL HAVE TO CHANGE SECOND PARAMETER BASED ON WHAT TYPE OF VALUE YOU WANT//////////////
            list.addAll(dq.getLatLngFromZone(i, 3));////////////////WILL HAVE TO CHANGE SECOND PARAMETER BASED ON WHAT TYPE OF VALUE YOU WANT//////////////
        }
        //test if they worked
        for(int i = 0; i < weightedList.size(); i++ ){
            Log.v("WeightedLatLng",i + " " + weightedList.get(i).toString());

        }
        for(int i = 0; i < list.size(); i++ ){
            Log.v("LatLng",i + " " + list.get(i).toString());
        }

        // Create the gradient.
        int[] colors = {
                Color.rgb(102, 225, 0), // green
                Color.rgb(255, 0, 0)    // red
        };

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String lowString = SP.getString("lowHumid","0");
        String highString = SP.getString("highHumid","100");
        float low,high;
        low = (float)Integer.valueOf(lowString);
        high = (float)Integer.valueOf(highString);
        float[] startPoints = {
                //////////////THIS WOULD CHANGE FROM MAP TO MAP> SHOULD GET FROM PREFERENCES///////////////////////////////////////////////////////////////////////////////
                low,high
        };
        Gradient gradient = new Gradient(colors, startPoints);
        // Create a heat map tile provider, passing it the latlngs
        if(weightedList != null) {
            mProvider = new HeatmapTileProvider.Builder()
                    .weightedData(weightedList)
                    .gradient(gradient)
                    //.data(list)
                    .build();
            // Add a tile overlay to the map, using the heat map tile provider.
            mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        }
        //addHeatMap();


        CameraPosition cp = new CameraPosition.Builder()
                .target(new LatLng(43.4557519, -76.5384007))
                .zoom(19)
                .bearing(90)
                .build();
        //mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new LatLng(43.4557519, -76.5384007), 19));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
        //Add polygon sections to the map
        //Section 1
        mMap.addPolygon(new PolygonOptions().add(new LatLng(43.4557519, -76.5384007),
                new LatLng(43.4557519, -76.5384007),
                new LatLng(43.4557003, -76.5380171),
                new LatLng(43.4557791, -76.5380023),
                new LatLng(43.4558502, -76.53795),
                new LatLng(43.4558911, -76.5379004),
                new LatLng(43.4559145, -76.5378977),
                new LatLng(43.4559213, -76.5379179),
                new LatLng(43.4559174, -76.5380922),
                new LatLng(43.4558989, -76.5381727),
                new LatLng(43.4557782, -76.5383886),
                new LatLng(43.4557519, -76.5384007)).strokeColor(Color.RED).fillColor(Color.LTGRAY));
        //Section 2
        mMap.addPolygon(new PolygonOptions().add(new LatLng(43.45586, -76.5390994),
                new LatLng(43.455784, -76.5389733),
                new LatLng(43.4557363, -76.5388137),
                new LatLng(43.4557237, -76.5386313),
                new LatLng(43.4557344, -76.5385602),
                new LatLng(43.4557607, -76.5384664),
                new LatLng(43.4559145, -76.5381995),
                new LatLng(43.4559067, -76.5383416),
                new LatLng(43.4558687, -76.5388419),
                new LatLng(43.4558911, -76.5388379),
                new LatLng(43.4558862, -76.5390833),
                new LatLng(43.4558473, -76.5386193),
                new LatLng(43.4557996, -76.53863),
                new LatLng(43.4557928, -76.538752),
                new LatLng(43.4557811, -76.5387507),
                new LatLng(43.4557782, -76.5388419),
                new LatLng(43.45586, -76.5390994)).strokeColor(Color.RED).fillColor(Color.LTGRAY));
        //Section 3
        mMap.addPolygon(new PolygonOptions().add(new LatLng(43.4558502, -76.5392335),
                new LatLng(43.4557753, -76.5392174),
                new LatLng(43.4556584, -76.5392026),
                new LatLng(43.4555504, -76.5391946),
                new LatLng(43.4555348, -76.5391731),
                new LatLng(43.4555358, -76.5391114),
                new LatLng(43.4555484, -76.5389934),
                new LatLng(43.4555757, -76.538862),
                new LatLng(43.4556059, -76.5387587),
                new LatLng(43.4556672, -76.5386099),
                new LatLng(43.4556818, -76.5385978),
                new LatLng(43.4557032, -76.5386166),
                new LatLng(43.4557052, -76.5387131),
                new LatLng(43.4557149, -76.5388432),
                new LatLng(43.455749, -76.5389559),
                new LatLng(43.4557947, -76.5390417),
                new LatLng(43.4558541, -76.5391155),
                new LatLng(43.4558502, -76.5392335)).strokeColor(Color.RED).fillColor(Color.LTGRAY));
        //Section 4
        mMap.addPolygon(new PolygonOptions().add(new LatLng(43.4555221, -76.5391597),
                new LatLng(43.4555027, -76.5391302),
                new LatLng(43.455527, -76.5381955),
                new LatLng(43.4556701, -76.538001),
                new LatLng(43.4556925, -76.5380305),
                new LatLng(43.4557178, -76.5382196),
                new LatLng(43.4557334, -76.5383644),
                new LatLng(43.4557129, -76.5384878),
                new LatLng(43.4556429, -76.5386434),
                new LatLng(43.4555835, -76.5387976),
                new LatLng(43.4555397, -76.5389934),
                new LatLng(43.4555221, -76.5391597)).strokeColor(Color.RED).fillColor(Color.LTGRAY));
        //Set bounds so the user can't go to the other side of the world.

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(43.4557519, -76.5384007)));
        //LatLngBounds boundary = new LatLngBounds(boundA, boundB);
        //mMap.setLatLngBoundsForCameraTarget(boundary);
        //Add a marker for each section
        //Section 1
        mMap.addMarker(new MarkerOptions().position(new LatLng(43.4557519, -76.5384007)).title("Section 1"));
        //Section 2
        mMap.addMarker(new MarkerOptions().position(new LatLng(43.45586, -76.5390994)).title("Section 2"));
        //Section 3
        mMap.addMarker(new MarkerOptions().position(new LatLng(43.4558502, -76.5392335)).title("Section 3"));
        //Section 4
        mMap.addMarker(new MarkerOptions().position(new LatLng(43.4555221, -76.5391597)).title("Section 4"));


    }

    private void addHeatMap(){
        DataQuery dq = new DataQuery();
        List<WeightedLatLng> weightedList ;
        List<LatLng> list;
        //populate the lists with a priming read
        weightedList = (dq.getWeightedFromZone(1, 3));////////////////WILL HAVE TO CHANGE SECOND PARAMETER BASED ON WHAT TYPE OF VALUE YOU WANT//////////////
        list = (dq.getLatLngFromZone(1, 3));////////////////WILL HAVE TO CHANGE SECOND PARAMETER BASED ON WHAT TYPE OF VALUE YOU WANT//////////////
        for(int i = 2; i < 5; i++) {
            weightedList.addAll(dq.getWeightedFromZone(i, 3));////////////////WILL HAVE TO CHANGE SECOND PARAMETER BASED ON WHAT TYPE OF VALUE YOU WANT//////////////
            list.addAll(dq.getLatLngFromZone(i, 3));////////////////WILL HAVE TO CHANGE SECOND PARAMETER BASED ON WHAT TYPE OF VALUE YOU WANT//////////////
        }

        //test if they worked
        for(int i = 0; i < weightedList.size(); i++ ){
            Log.v("WeightedLatLng",i + " " + weightedList.get(i).toString());

        }
        for(int i = 0; i < list.size(); i++ ){
            Log.v("LatLng",i + " " + list.get(i).toString());
        }

        // Create the gradient.
        int[] colors = {
                Color.rgb(102, 225, 0), // green
                Color.rgb(255, 0, 0)    // red
        };

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String lowString = SP.getString("lowHumid","0");
        String highString = SP.getString("highHumid","100");
        float low,high;
        low = (float)Integer.valueOf(lowString);
        high = (float)Integer.valueOf(highString);
        float[] startPoints = {
                //////////////THIS WOULD CHANGE FROM MAP TO MAP> SHOULD GET FROM PREFERENCES///////////////////////////////////////////////////////////////////////////////
                low,high
        };

        Gradient gradient = new Gradient(colors, startPoints);
        // Create a heat map tile provider, passing it the latlngs
        if(weightedList != null) {
            mProvider = new HeatmapTileProvider.Builder()
                    .weightedData(weightedList)
                    .gradient(gradient)
                    //.data(list)
                    .build();
            // Add a tile overlay to the map, using the heat map tile provider.
            mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        }




    }
}
