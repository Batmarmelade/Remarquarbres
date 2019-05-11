package com.esaip.remarquarbres;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class GPSActivity extends AppCompatActivity {

    Location currentPosition;
    TextView ed_latitude;
    TextView ed_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        ed_latitude = findViewById(R.id.TV_gps_latitude);
        ed_longitude = findViewById(R.id.TV_gps_longitude);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener onLocationChange = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {                                      //dès que le gps bouge
                ed_latitude.setText(getString(R.string.tv_latitude) + Double.toString(location.getLatitude()));        //affiche à l'écrant les coordonées
                ed_longitude.setText(getString(R.string.tv_longitude) + Double.toString(location.getLongitude()));
                findViewById(R.id.BT_gps_continue).setVisibility(View.VISIBLE);                     //rend boutton continuer visible dès qu'il y a un résultat
                currentPosition = location;                                                         //enregistre la position pour l'intent
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
        };

        //verification des permission gps
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //demmande de permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 3);
        }
        else {
            //appel de onLocationUpdate toutes les 0 ms et 0 m
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, onLocationChange);
        }
    }

    public void gpsContinue(View view){
        //intent vers le quiz avec les coordonées
        Intent intent = new Intent(getApplicationContext(), Quiz.class);
        intent.putExtra("Latitude",currentPosition.getLatitude());
        intent.putExtra("Longitude",currentPosition.getLongitude());
        startActivity(intent);
    }
}
