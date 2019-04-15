package com.esaip.remarquarbres;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Log.d("D", "I'm in the Quiz");

    }

    public void submit(android.view.View v){
        Intent returnIntent = getIntent();
        returnIntent.putExtra("Nom", ((EditText) findViewById(R.id.NomTxtEd)).getText().toString() );
        returnIntent.putExtra("Pseudo", ((EditText) findViewById(R.id.PseudoTxtEd)).getText().toString() );
        returnIntent.putExtra("Mail", ((EditText) findViewById(R.id.MailTxtEd)).getText().toString() );
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
