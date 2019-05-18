package com.esaip.remarquarbres;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Stockage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockage);

        HashMap<String, String> answers = new HashMap<String, String>();

        Intent data = getIntent();
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            Log.e("RESULTS","Dumping Intent start");
            while (it.hasNext()) {
                String key = it.next();
                Log.i("RESULTS","[" + key + "=" + bundle.get(key)+"]");
                ((TextView) findViewById(R.id.DataDump)).append("\n[" + key + "=" + bundle.get(key)+"]");
                answers.put(key, bundle.get(key).toString());
            }
            Log.e("RESULTS","Dumping Intent end");
        }

        try {
            exportInCSV(answers);
        } catch (IOException e) {
            Log.e("IO ERROR", "IO Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void exportInCSV(HashMap<String, String> answers) throws IOException {
        final HashMap<String, String> data = answers;

        File folderArbres = new File(Environment.getExternalStorageDirectory() + "/Arbres");
        Log.d("PATH", "PATH : " + folderArbres.toString());
        // On crée ici le dossier s'il n'existe pas
        boolean var = false;
        if (!folderArbres.exists()){
            var = folderArbres.mkdir();
        }
        Log.d("IO : FOLDER CREATION", "Folder created : " + var);

        File folder = new File(Environment.getExternalStorageDirectory() + "/Arbres" + "/Arbre" + Math.abs(new Random().nextInt()));
        Log.d("PATH", "PATH : " + folder.toString());
        // On crée ici le dossier s'il n'existe pas
        var = false;
        if (!folder.exists()){
            var = folder.mkdir();
        }
        Log.d("IO : FOLDER CREATION", "Folder created : " + var);

        final String infosName = folder.toString() + "/" + "Infos" + Math.abs(new Random().nextInt()) + ".txt";
        Log.d("FOLDER CREATION", infosName);
        File info = new File(infosName);

        var = false;
        if(!info.exists()) {
            var = info.createNewFile();
        }
        Log.d("IO : FILE CREATION", "File info created : " + var);

        // show waiting screen
        CharSequence contentTitle = getString(R.string.app_name);
        final ProgressDialog progDialog = ProgressDialog.show(
                Stockage.this, contentTitle, "Allez-y, et que rien ne vous frêne !",
                true);//please wait
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
            }
        };

        new Thread() {
            public void run() {
                try {

                    FileWriter fw = new FileWriter(infosName);

                    for ( String question : data.keySet() ){
                        fw.append(question + " : " + (data.get(question)) + "\n");
                    }

                    fw.flush();
                    fw.close();
                } catch (Exception e) {
                    Log.e("IO ERROR", e.getMessage());
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
                progDialog.dismiss();
            }
        }.start();

        // Copy image to correct folder
        copyImage(folder);

    }

    public void copyImage(File folder) {
        File srcImage = new File(getIntent().getStringExtra("ImageName"));
        File dstImage = new File(folder, srcImage.getName());

        try {
            dstImage.createNewFile();

            FileChannel source = null;
            FileChannel destination = null;

            try {
                source = new FileInputStream(srcImage).getChannel();
                destination = new FileOutputStream(dstImage).getChannel();
                destination.transferFrom(source, 0, source.size());
            } finally {
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
            }
        } catch (IOException e) {
            Log.e("COPY ERROR", e.getMessage());
            e.printStackTrace();
        }

    }
}
