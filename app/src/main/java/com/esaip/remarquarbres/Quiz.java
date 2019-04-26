package com.esaip.remarquarbres;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Quiz extends AppCompatActivity {

    private EditText dateEd;
    private DatePickerDialog dpDialog;
    private SimpleDateFormat dateFormatter;
    private final String[] intitulesQuestions = {"Nom", "Pseudo", "Mail", "Nom de l'arbre", "Nom Botanique", "Latitude", "Longitude", "Addresse", "Emplacement", "Remarquable", "Biodiversité", "Remarquabilité", "Observations", "VérifBotaniste", "Date"};
    private final String[] obligatoires = {"Nom", "Addresse", "Emplacement", "Remarquabilité", "VérifBotaniste", "Date"};
    private HashMap<String, String> réponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        final EditText ed_latitude = findViewById(R.id.LatitudeTxtEd);
        final EditText ed_longitude = findViewById(R.id.LongitudeTxtEd);


        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener onLocationChange = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                ed_latitude.setText(Double.toString(location.getLatitude()));
                ed_longitude.setText(Double.toString(location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 3);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, onLocationChange);
        }

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

        this.réponses = new HashMap<>();

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_1);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        final int height = size.y;

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                double scrollViewHeight = scrollView.getChildAt(0).getBottom() - scrollView.getHeight();
                double getScrollY = scrollView.getScrollY();
                double scrollPosition = (getScrollY / scrollViewHeight) * 100d;
                progressBar.setProgress((int) scrollPosition);
            }
        });
    }

    public void submit(android.view.View v){

        Intent returnIntent = new Intent(Quiz.this, Save_File.class);

        // Nom & Prénom
        this.réponses.put("Nom", ((EditText) findViewById(R.id.NomTxtEd)).getText().toString() );

        // Pseudonyme
        this.réponses.put("Pseudo", ((EditText) findViewById(R.id.PseudoTxtEd)).getText().toString() );

        // Addresse Mail
        this.réponses.put("Mail", ((EditText) findViewById(R.id.MailTxtEd)).getText().toString() );

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
        this.réponses.put("Nom de l'arbre", strNomArbre);

        // Nom Botanique de l'arbre
        this.réponses.put("Nom Botanique", ((EditText) findViewById(R.id.BotaniqueTxtEd)).getText().toString() );

        // Latitude
        this.réponses.put("Latitude", ((EditText) findViewById(R.id.LatitudeTxtEd)).getText().toString() );

        // Longitude
        this.réponses.put("Longitude", ((EditText) findViewById(R.id.LongitudeTxtEd)).getText().toString() );

        // Addresse
        this.réponses.put("Addresse", ((EditText) findViewById(R.id.AddresseTxtEd)).getText().toString() );

        // Emplacement
        RadioGroup emplacementsRG = (RadioGroup) findViewById(R.id.EmplacementsRG);
        String strEmplacement = "";
        if ( emplacementsRG.getCheckedRadioButtonId() != -1 ) {
            RadioButton rbChecked = (RadioButton) findViewById(emplacementsRG.getCheckedRadioButtonId());
            strEmplacement = rbChecked.getText().toString();
        }
        this.réponses.put("Emplacement", strEmplacement);

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
        this.réponses.put("Remarquable", strRemarquable);

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
        this.réponses.put("Biodiversité", strBiodiversité);

        // Remarquabilité
        RadioGroup remarquabilitésRG = (RadioGroup) findViewById(R.id.RemarquabilitésRG);
        String strRemarquabilité = "";
        if ( remarquabilitésRG.getCheckedRadioButtonId() != -1 ) {
            RadioButton rbChecked = (RadioButton) findViewById(remarquabilitésRG.getCheckedRadioButtonId());
            strRemarquabilité = rbChecked.getText().toString();
        }
        this.réponses.put("Remarquabilité", strRemarquabilité);

        // Observations
        this.réponses.put("Observations", ((EditText) findViewById(R.id.ObservationsTxtEd)).getText().toString() );

        // Verification Infos Botaniste
        RadioGroup verifBotanisteRG = (RadioGroup) findViewById(R.id.VerifBotanisteRG);
        String strVerifBotaniste = "";
        if ( verifBotanisteRG.getCheckedRadioButtonId() != -1 ) {
            RadioButton rbChecked = (RadioButton) findViewById(verifBotanisteRG.getCheckedRadioButtonId());
            strVerifBotaniste = rbChecked.getText().toString();
        }
        this.réponses.put("VérifBotaniste", strVerifBotaniste);

        // Date
        this.réponses.put("Date", ((EditText) findViewById(R.id.DateEd)).getText().toString() );

        // Check for mandatory answers
        Boolean allAnswered = true;
        for( String s : this.obligatoires ){
            if ( this.réponses.get(s) == null || this.réponses.get(s).equals("") )
                allAnswered = false;
        }

        // Check for Date not posterior to today
        Boolean correctDate = true;
        Calendar newDate = Calendar.getInstance();
        String dateToday = dateFormatter.format(newDate.getTime());
        String dateEntered = this.réponses.get("Date");
        String[] dateTodaySplitted = dateToday.split("-");
        String[] dateEnteredSplitted = dateEntered.split("-");
        for ( int i = 0; i < dateTodaySplitted.length; i ++){
            int tempIntToday = Integer.parseInt(dateTodaySplitted[i]);
            int tempIntEntered = Integer.parseInt(dateEnteredSplitted[i]);
            if ( tempIntToday < tempIntEntered )
                correctDate = false;
        }

        if ( allAnswered && correctDate ){
            for( String s : intitulesQuestions ){
                returnIntent.putExtra(s, this.réponses.get(s));
            }
            startActivity(returnIntent);
        } else {
            String strError = "";
            if ( ! allAnswered )
                strError += "Il faut répondre à toutes les questions obligatoires !\n";
            if ( ! correctDate )
                strError += "La date entrée est supérieure à celle d'aujourd'hui, vous êtes dans le futur ?";
            Toast.makeText(this, strError, Toast.LENGTH_SHORT).show();
        }
        //Ajout de la création de CSV
        /*
            send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FilePath = context.getFilesDir() + "/" + "hello.txt";
                String FileLocation = "quelqueChose.zip";
                //Les filepath et location sont à changer
                zipFileAtPath(FilePath, FileLocation);
                File zipped = new File(FileLocation);
                composeEmail("arbresanjou@netc.fr", "Recensement arbre remarquable", Uri.fromFile(zipped));
                //intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/path/to/file")));

            }
        });
    }

    public void composeEmail(String adresse, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*rajouter / une fois décommenté*");
        intent.putExtra(Intent.EXTRA_EMAIL, adresse);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public boolean zipFileAtPath(String sourcePath, String toLocation) {
        final int BUFFER = 2048;

        File sourceFile = new File(sourcePath);
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(toLocation);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            byte data[] = new byte[BUFFER];
            FileInputStream fi = new FileInputStream(sourcePath);
            origin = new BufferedInputStream(fi, BUFFER);
            ZipEntry entry = new ZipEntry(getLastPathComponent(sourcePath));
            entry.setTime(sourceFile.lastModified()); // to keep modification time after unzipping
            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public String getLastPathComponent(String filePath) {
        String[] segments = filePath.split("/");
        if (segments.length == 0)
            return "";
        String lastPathComponent = segments[segments.length - 1];
        return lastPathComponent;
         */
    }

    public void deployDate(android.view.View v){
        dpDialog.show();
    }
}
