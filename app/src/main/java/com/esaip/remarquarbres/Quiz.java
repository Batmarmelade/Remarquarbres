package com.esaip.remarquarbres;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

        final CheckBox choixRemarquableAutreChBx = (CheckBox) findViewById(R.id.RemarquableAutreChBx);
        final EditText choixRemarquableAutreEd = (EditText) findViewById(R.id.RemarquableAutreEd);
        choixRemarquableAutreChBx.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if ( compoundButton.isChecked() ){
                    choixRemarquableAutreChBx.setText(R.string.RemarquableAutreSelected);
                    choixRemarquableAutreEd.setVisibility(View.VISIBLE);
                } else {
                    choixRemarquableAutreChBx.setText(R.string.RemarquableAutre);
                    choixRemarquableAutreEd.setVisibility(View.GONE);
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

        // Emplacement
        RadioGroup emplacementsRG = (RadioGroup) findViewById(R.id.EmplacementsRG);
        String strEmplacement = "";
        if ( emplacementsRG.getCheckedRadioButtonId() != -1 ) {
            RadioButton rbChecked = (RadioButton) findViewById(emplacementsRG.getCheckedRadioButtonId());
            strEmplacement = rbChecked.getText().toString();
        }
        returnIntent.putExtra("Emplacement", strEmplacement);

        // Remarquable
        String strRemarquable = "";
        Integer[] remarquableCheckBoxesIds = {R.id.Remarquable1ChBx, R.id.Remarquable2ChBx, R.id.Remarquable3ChBx, R.id.Remarquable4ChBx, R.id.Remarquable5ChBx, R.id.Remarquable6ChBx, R.id.RemarquableAutreChBx};

        for ( Integer i : remarquableCheckBoxesIds ){
            CheckBox temp = (CheckBox) findViewById(i);
            if ( temp.isChecked() ){
                if ( i == R.id.RemarquableAutreChBx ) {
                    EditText remarquableAutreEd = (EditText) findViewById(R.id.RemarquableAutreEd);
                    if ( ! remarquableAutreEd.getText().toString().equals("") )
                        strRemarquable += remarquableAutreEd.getText().toString() + ";";
                } else {
                    strRemarquable += temp.getText() + ";";
                }
            }
        }
        returnIntent.putExtra("Remarquable", strRemarquable);

        /*
         *
         * TO DO
         *           Biodiversité
         */
        // Remarquabilité
        RadioGroup remarquabilitésRG = (RadioGroup) findViewById(R.id.RemarquabilitésRG);
        String strRemarquabilité = "";
        if ( remarquabilitésRG.getCheckedRadioButtonId() != -1 ) {
            RadioButton rbChecked = (RadioButton) findViewById(remarquabilitésRG.getCheckedRadioButtonId());
            strRemarquabilité = rbChecked.getText().toString();
        }
        returnIntent.putExtra("Remarquabilité", strRemarquabilité);

        // Observations
        returnIntent.putExtra("Observations", ((EditText) findViewById(R.id.ObservationsTxtEd)).getText().toString() );

        // Verification Infos Botaniste
        RadioGroup verifBotanisteRG = (RadioGroup) findViewById(R.id.VerifBotanisteRG);
        String strVerifBotaniste = "";
        if ( verifBotanisteRG.getCheckedRadioButtonId() != -1 ) {
            RadioButton rbChecked = (RadioButton) findViewById(verifBotanisteRG.getCheckedRadioButtonId());
            strVerifBotaniste = rbChecked.getText().toString();
        }
        returnIntent.putExtra("Vérification Infos Botaniste", strVerifBotaniste);

        // TO DO DATE


        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
