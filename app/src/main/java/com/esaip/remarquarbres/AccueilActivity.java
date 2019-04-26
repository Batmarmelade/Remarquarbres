package com.esaip.remarquarbres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccueilActivity extends AppCompatActivity {
private Button button;

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

        /*mQuiz.setOnClickListener(new View.OnClickListener() {
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
        });*/


    }


}
