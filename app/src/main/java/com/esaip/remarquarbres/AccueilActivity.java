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

        Intent intent = new Intent(this, Quiz.class);
        startActivityForResult(intent, code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK ){
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Set<String> keys = bundle.keySet();
                Iterator<String> it = keys.iterator();
                Log.e("RESULTS","Dumping Intent start");
                while (it.hasNext()) {
                    String key = it.next();
                    Log.e("RESULTS","[" + key + "=" + bundle.get(key)+"]");
                    ((TextView) findViewById(R.id.DataDump)).append("\n[" + key + "=" + bundle.get(key)+"]");
                }
                Log.e("RESULTS","Dumping Intent end");
            }
        }
    }
}
