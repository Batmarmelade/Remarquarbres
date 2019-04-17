package com.esaip.remarquarbres;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Quiz extends AppCompatActivity {

    private EditText dateEd;
    private DatePickerDialog dpDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        dateEd = (EditText) findViewById(R.id.DateEd);
        Calendar tempDate = Calendar.getInstance();
        dateEd.setText(dateFormatter.format(tempDate.getTime()));

        Calendar newCalendar = Calendar.getInstance();
        dpDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateEd.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        dateEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    dpDialog.show();
                v.clearFocus();
            }
        });

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

        final CheckBox RemarquableAutreChBx = (CheckBox) findViewById(R.id.RemarquableAutreChBx);
        final EditText RemarquableAutreEd = (EditText) findViewById(R.id.RemarquableAutreEd);
        RemarquableAutreChBx.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if ( compoundButton.isChecked() ){
                    RemarquableAutreChBx.setText(R.string.RemarquableAutreSelected);
                    RemarquableAutreEd.setVisibility(View.VISIBLE);
                } else {
                    RemarquableAutreChBx.setText(R.string.RemarquableAutre);
                    RemarquableAutreEd.setVisibility(View.GONE);
                }
            }
        });

        final CheckBox biodiversitéAutreChBx = (CheckBox) findViewById(R.id.BiodiversitéAutreChBx);
        final EditText biodiversitéAutreEd = (EditText) findViewById(R.id.BiodiversitéAutreEd);
        biodiversitéAutreChBx.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if ( compoundButton.isChecked() ){
                    biodiversitéAutreChBx.setText(R.string.BiodiversitéAutreSelected);
                    biodiversitéAutreEd.setVisibility(View.VISIBLE);
                } else {
                    biodiversitéAutreChBx.setText(R.string.BiodiversitéAutre);
                    biodiversitéAutreEd.setVisibility(View.GONE);
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

        // Biodiversité
        String strBiodiversité = "";
        Integer[] biodiversitéCheckBoxesIds = {R.id.Biodiversité1ChBx, R.id.Biodiversité2ChBx, R.id.Biodiversité3ChBx, R.id.Biodiversité4ChBx, R.id.Biodiversité5ChBx, R.id.BiodiversitéAutreChBx};

        for ( Integer i : biodiversitéCheckBoxesIds ){
            CheckBox temp = (CheckBox) findViewById(i);
            if ( temp.isChecked() ){
                if ( i == R.id.BiodiversitéAutreChBx ) {
                    EditText biodiversitéAutreEd = (EditText) findViewById(R.id.BiodiversitéAutreEd);
                    if ( ! biodiversitéAutreEd.getText().toString().equals("") )
                        strBiodiversité += biodiversitéAutreEd.getText().toString() + ";";
                } else {
                    strBiodiversité += temp.getText() + ";";
                }
            }
        }
        returnIntent.putExtra("Biodiversité", strBiodiversité);

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

        // Date
        returnIntent.putExtra("Date", ((EditText) findViewById(R.id.DateEd)).getText().toString() );

        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void deployDate(android.view.View v){
        dpDialog.show();
    }
}
