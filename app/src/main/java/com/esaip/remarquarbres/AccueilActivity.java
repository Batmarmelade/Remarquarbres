package com.esaip.remarquarbres;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;

public class AccueilActivity extends AppCompatActivity {

    public final static int code = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Intent i = new Intent(this,PhotoActivity.class);
        startActivity(i);

    }


}
