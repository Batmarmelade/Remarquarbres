package com.esaip.remarquarbres;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.AlteredCharSequence;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

 /* Test pour changer l'activité GPS en simple classe java

public class GPS2 extends Context implements LocationListener{

    public Location Position(){

        LocationManager locationManager;

        if(ContextCompat.checkSelfPermission(GPS2.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(GPS2.this,"en attente de la permission pour utiliser le GPS", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
                return null;
            }
            return null;
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGPSEnabled) {
                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, listener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return location;
            } else {
                Toast.makeText(GPS2.this, "Le GPS est désactivé", Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        t.setText("Longitude : " + longitude + "\n" + "Latitude : " + latitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
*/