package com.example.bianca.googlemaps;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.bianca.googlemaps.Database.MarkerDAO;
import com.example.bianca.googlemaps.Database.MarkerMaps;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExemploProviderFragmentV1 extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, LocationListener {


    private GoogleMap mMap;
    private static final String  TAG = "ExemploProvFragmentV1";
    private LocationManager locationManager;
    private Location location;
    private boolean request;
    private String text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);

        try {
            locationManager= (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, this);
        }catch(SecurityException ex){
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
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
        LatLng locationCurrent = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(locationCurrent);
        markerOptions.title("Posição Atual");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder)); //alterando ícone.

        Marker position= mMap.addMarker(markerOptions);
        position.showInfoWindow(); // mostrar informações do marcador.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationCurrent));
    }

    @Override
    public void onMapClick(final LatLng latLng) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deseja adicionar um marcador ?")
                .setTitle("Adicionar marcador")
                .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                final AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                final View v = View.inflate(getActivity(), R.layout.dialog_select_marker, null);
                final BootstrapEditText editText = (BootstrapEditText) v.findViewById(R.id.edNameMarker);
                text = editText.getText().toString();

                //show dialogs.
                alert.setView(v);
                alert.show();

                ImageButton buttonMarker1 = v.findViewById(R.id.imgBtn1);
                ImageButton buttonMarker2 = v.findViewById(R.id.imgBtn2);
                ImageButton buttonMarker3 = v.findViewById(R.id.imgBtn3);
                ImageButton buttonMarker4 = v.findViewById(R.id.imgBtn4);

                buttonMarker1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        putMarker(latLng, v,1);
                    }
                });

                buttonMarker2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        putMarker(latLng, v,2);
                    }
                });


                buttonMarker3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        putMarker(latLng, v,3);
                    }
                });

                buttonMarker4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        putMarker(latLng, v,4);
                    }
                });

            }
         })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        }

    private void saveDatabase(LatLng latLng, String text, int num) {
        try {
            MarkerMaps markerMaps = new MarkerMaps();
            markerMaps.setLatitude(latLng.latitude);
            markerMaps.setLongitude(latLng.longitude);
            markerMaps.setTitle(text);
            markerMaps.setDate(timeNow());
            markerMaps.setImage(num);
            new MarkerDAO().save(markerMaps);
        }catch (Exception e){
            Toast.makeText(getContext(),"Tente novamente !", Toast.LENGTH_LONG).show();
        }
    }

    private String timeNow(){
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();

        return dateFormat_hora.format(data_atual);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void putMarker(LatLng latLng, View v, int numMarker){
        final BootstrapEditText editText = (BootstrapEditText) v.findViewById(R.id.edNameMarker);
        BitmapDescriptor markerIcon = getBitmapDescriptor(numMarker);

        if(editText.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Preencha um título para o marcador !")
                    .setTitle("Título vazio")
                    .setNeutralButton("OK",null)
                    .show();
        }else{
            if(markerIcon != null) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(editText.getText().toString());
                markerOptions.icon(markerIcon);
                Marker position = mMap.addMarker(markerOptions);
                text = editText.getText().toString();
                request=true;
            }else{
                Toast.makeText(getContext(),"Nenhuma seleção de marcador",Toast.LENGTH_LONG).show();
            }
        }

        if(request){
            saveDatabase(latLng,text,numMarker);
        }
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private BitmapDescriptor getBitmapDescriptor(int numMarker){
        BitmapDescriptor markerIcon = null;
        Drawable marker;
        switch (numMarker){
            case 1:
                marker = getResources().getDrawable(R.drawable.marcador);
                markerIcon = getMarkerIconFromDrawable(marker);
                return markerIcon;
            case 2:
                marker = getResources().getDrawable(R.drawable.pin);
                markerIcon = getMarkerIconFromDrawable(marker);
                return markerIcon;
            case 3:
                marker = getResources().getDrawable(R.drawable.favorite);
                markerIcon = getMarkerIconFromDrawable(marker);
                return markerIcon;
            case 4:
                marker = getResources().getDrawable(R.drawable.marker2);
                markerIcon = getMarkerIconFromDrawable(marker);
                return markerIcon;
        }
         return null;
    }
}
