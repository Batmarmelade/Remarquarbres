package com.esaip.remarquarbres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TrackingMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_method);
    }

    public void choseGPS(View view){
        Intent intent = new Intent(TrackingMethodActivity.this, GPSActivity.class);
        startActivity(intent);
    }

    public void choseMap(View view){
        Intent intent = new Intent(TrackingMethodActivity.this, MapSelectionActivity.class);
        startActivity(intent);
    }

    public void choseManual(View view){
        Intent intent = new Intent(TrackingMethodActivity.this, Quiz.class);
        intent.putExtra("ImageName", getIntent().getStringExtra("ImageName"));
        startActivity(intent);
    }
}
