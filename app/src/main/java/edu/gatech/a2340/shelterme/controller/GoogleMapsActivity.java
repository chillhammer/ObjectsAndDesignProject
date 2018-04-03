package edu.gatech.a2340.shelterme.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import edu.gatech.a2340.shelterme.Model.Shelter;
import edu.gatech.a2340.shelterme.R;

@SuppressWarnings("FeatureEnvy")
public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    // --Commented out by Inspection (4/2/2018 6:04 PM):private GoogleMap mMap;

    private List<Shelter> bufferedSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        Intent intent = getIntent();

        bufferedSearch = (List<Shelter>) intent.getSerializableExtra("shelters");


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mMap = googleMap;

        for (Shelter shelter : bufferedSearch) {
            Double latitude = Double.parseDouble(shelter.getLatitude());
            Double longitude = Double.parseDouble(shelter.getLongitude());
            LatLng location = new LatLng(latitude, longitude);
            String title = shelter.getName();
            String snippet = shelter.getPhone() + "\n" +  shelter.getVacancies() + "vacancies";
            googleMap.addMarker(new MarkerOptions().position(location).title(title).
                    snippet(snippet));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
