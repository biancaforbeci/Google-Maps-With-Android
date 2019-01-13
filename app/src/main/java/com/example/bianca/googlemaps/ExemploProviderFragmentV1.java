package com.example.bianca.googlemaps;


import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExemploProviderFragmentV1 extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener{


    private GoogleMap mMap;
    private static final String  TAG = "ExemploProvFragmentV1";
    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE); //pega o serviço de localização

            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            Toast.makeText(getContext(),"Provider:" +provider.toString(),Toast.LENGTH_SHORT).show();

            mMap = googleMap;
            mMap.setOnMapClickListener(this);
            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.setMyLocationEnabled(true);
        }catch (SecurityException ex){
            Log.e(TAG,"Error",ex);
        }
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(sydney);
        markerOptions.title("Marker in Sydney");

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getContext(),"Coordenadas: " + latLng.toString(),Toast.LENGTH_SHORT).show();
    }
}
