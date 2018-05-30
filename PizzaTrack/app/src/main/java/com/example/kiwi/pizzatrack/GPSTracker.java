package com.example.kiwi.pizzatrack;

/**
 * Created by kiwi_ on 19/1/2018.
 */

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by kiwi on 18/1/2018.
 */

public class GPSTracker extends Service implements LocationListener {

    private final Context mConext;

    boolean isGPSEnable = false; //Para saber si esta habilutado GPS
    boolean isNetworkEnable = false; //Para saber si esta habilitado la red
    boolean canGetLocation = false; //Saber si puede tener la locacion

    Location location;
    public static double latitude;
    public static double longitude;

    private static final long minDistancia = 10; //Distancia minima para actualizar 10 metros
    private static  final long minTiempo = 1000 * 60 * 1; //Tiempo minimo para actualizar 1 minuto
    protected LocationManager locationManager;

    public GPSTracker(Context context){
        this.mConext = context;
        getLocation();
    }

    public Location getLocation(){
        try{
            if (ContextCompat.checkSelfPermission(mConext, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                Toast.makeText(mConext,"Permiso Denegado",Toast.LENGTH_SHORT).show();
                return null;
            }

            locationManager = (LocationManager) mConext.getSystemService(LOCATION_SERVICE);
            isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(isGPSEnable){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                return location;
            }
            else{
                Toast.makeText(mConext,"Por favor habilite el GPS",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public double getLatitude(){
        if(location!=null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location!=null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean CanGetLocation(){
        return this.canGetLocation;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
}
