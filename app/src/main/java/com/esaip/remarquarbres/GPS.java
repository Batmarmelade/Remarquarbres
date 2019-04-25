
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

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class GPS extends AppCompatActivity {


    private Button b;
    private TextView t;
    private LocationManager locationManager;
    private LocationListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);

        t = (TextView) findViewById(R.id.textView);
        b = (Button) findViewById(R.id.button);

        listener = new LocationListener() {
            public void onLocationChanged(Location location) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                t.setText("Longitude : " + longitude + "\n" + "Latitude : " + latitude); // écrit les coordonnées dans la textbox
            }

            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            public void onProviderEnabled(String s) {

            }

            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(GPS.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { //vérifie si l'application peut utiliser le GPS
                    Toast.makeText(GPS.this,"en attente de la permission pour utiliser le GPS", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                                ,10); //demande la permission d'accéder aux coordonnées GPS
                    }
                } else {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                    if (isGPSEnabled) { //vérifie si le GPS est alumé
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, listener); //appelle la fonction OnLocationChange de listener
                        //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //enregistre la location dans un objet location
                    } else {
                        Toast.makeText(GPS.this, "Le GPS est désactivé", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}