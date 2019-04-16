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

        // Nom & Prénom
        returnIntent.putExtra("Nom & Prénom", ((EditText) findViewById(R.id.NomTxtEd)).getText().toString() );

        // Pseudonyme
        returnIntent.putExtra("Pseudo", ((EditText) findViewById(R.id.PseudoTxtEd)).getText().toString() );

        // Addresse Mail
        returnIntent.putExtra("Mail", ((EditText) findViewById(R.id.MailTxtEd)).getText().toString() );

        // Nom de l'arbre
        String strNomArbre = "";
        RadioGroup choixArbresRG = (RadioGroup) findViewById(R.id.ArbresRG);
        RadioButton rbArbreAutre = (RadioButton) findViewById(R.id.ArbresChoixAutreRb);
        if ( rbArbreAutre.isChecked() ){
            strNomArbre = "Autre, ";
            strNomArbre = strNomArbre.concat(((EditText) findViewById(R.id.ArbresAutreEd)).getText().toString());
        } else {
            if ( choixArbresRG.getCheckedRadioButtonId() != -1 ) {
                RadioButton rbChecked = (RadioButton) findViewById(choixArbresRG.getCheckedRadioButtonId());
                strNomArbre = rbChecked.getText().toString();
            }
        }
        returnIntent.putExtra("Nom de l'arbre", strNomArbre);

        // Nom Botanique de l'arbre
        returnIntent.putExtra("Nom Botanique", ((EditText) findViewById(R.id.BotaniqueTxtEd)).getText().toString() );

        // Latitude
        returnIntent.putExtra("Latitude", ((EditText) findViewById(R.id.LatitudeTxtEd)).getText().toString() );

        // Longitude
        returnIntent.putExtra("Longitude", ((EditText) findViewById(R.id.LongitudeTxtEd)).getText().toString() );

        // Addresse
        returnIntent.putExtra("Addresse", ((EditText) findViewById(R.id.AddresseTxtEd)).getText().toString() );

        /*
         *
         * TO DO
         *
         */

        // Observations
        returnIntent.putExtra("Observations", ((EditText) findViewById(R.id.ObservationsTxtEd)).getText().toString() );
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
