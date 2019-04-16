package com.esaip.remarquarbres;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Log.d("D", "I'm in the Quiz");

        RadioGroup choixArbresRG = (RadioGroup) findViewById(R.id.ArbresRG);
        choixArbresRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                EditText arbresAutreEd = (EditText) findViewById(R.id.ArbresAutreEd);
                RadioButton rbArbreAutre = (RadioButton) findViewById(R.id.ArbresChoixAutreRb);
                if (rbArbreAutre.isChecked()) {
                    rbArbreAutre.setText(R.string.ArbreAutreSelected);
                    arbresAutreEd.setVisibility(View.VISIBLE);
                } else {
                    rbArbreAutre.setText(R.string.ArbreAutre);
                    arbresAutreEd.setVisibility(View.GONE);
                }
            }
        });

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
