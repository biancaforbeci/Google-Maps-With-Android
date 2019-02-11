package com.example.bianca.googlemaps;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExemploProviderFragmentV2GPS extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, LocationListener {


    private GoogleMap mMap;
    private static final String TAG = "ExemploProvFragmentV1";
    private LocationManager locationManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
        configurarServico();

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setMessage("ZOOM")
                .setTitle("Utilize os botões laterais para dar zoom e ver o trânsito atual na região que deseja.")
                .setNeutralButton("OK",null)
                .show();

    }

    public void configurarServico(){
        try {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }catch(SecurityException ex){
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);
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

            mMap = googleMap;
            mMap.setOnMapClickListener(this);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);
            mMap.setTrafficEnabled(true);

            LatLng Brazil = new LatLng(-14.235, -51.9253);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Brazil, 5));


        }catch (SecurityException ex){
            Log.e(TAG,"Error",ex);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getContext(),"Coordenadas: " + latLng.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getContext(),"Posição Alterada !",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Toast.makeText(getContext(),"O status do Provider foi alterado",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(getContext(),"Provider habilitado.",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(getContext(),"Provider desabilitado",Toast.LENGTH_LONG).show();
    }
}

