package com.esaip.remarquarbres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Iterator;
import java.util.Set;

public class AccueilActivity extends AppCompatActivity {

    private ImageButton img;
    private ImageButton mQuiz;
    private ImageButton imap;
    private ImageView mcopy;

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

                Intent testIntent = new Intent(AccueilActivity.this, Test.class );
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
                Intent testIntent = new Intent(AccueilActivity.this, Test.class );
                startActivity(testIntent);
            }
        });

        mcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(AccueilActivity.this, Test.class );
                startActivity(testIntent);
            }
        });

    }


}
