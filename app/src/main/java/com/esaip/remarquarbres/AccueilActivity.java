package com.esaip.remarquarbres;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;



public class AccueilActivity extends AppCompatActivity {

    private ImageButton img;
    private ImageButton mQuiz;
    private ImageButton imap;
    private ImageView mcopy;
    private String mapURl = "https://framacarte.org/fr/map/arbres-remarquables-vus-par-angers-loire-metropole_34990#14/";

    private ImageButton testMap;

    public final static int code = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        img = findViewById(R.id.img);
        mQuiz = findViewById(R.id.mQuiz);
        imap = findViewById(R.id.imap);
        mcopy = findViewById(R.id.mCopy);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent testIntent = new Intent(AccueilActivity.this, PhotoActivity.class );
                startActivity(testIntent);
            }
        });

        mQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(AccueilActivity.this, Test.class );
                startActivity(testIntent);
            }
        });

        imap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(mapURl);
            }
        });

        mcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(AccueilActivity.this, Test.class );
                startActivity(testIntent);
            }
        });

        testMap = findViewById(R.id.testMap);
        testMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(AccueilActivity.this, MapSelectionActivity.class );
                startActivity(testIntent);
            }
        });

    }



    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
