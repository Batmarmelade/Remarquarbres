package com.esaip.remarquarbres;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Save_File extends AppCompatActivity {
private Button send;
private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save__file);

        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            File zipped = createDir();
            composeEmail("mjosephine.ir2020@esaip.org", "Recensement arbre remarquable", Uri.fromFile(zipped));
                //intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/path/to/file")));

            }
        });
    }

    public void composeEmail(String adresse, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
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
		Toast zipOk = Toast.makeText(getApplicationContext(), "Fichier zippé", Toast.LENGTH_SHORT);
		toast.show();
    }
    public String getLastPathComponent(String filePath) {
        String[] segments = filePath.split("/");
        if (segments.length == 0)
            return "";
        String lastPathComponent = segments[segments.length - 1];
        return lastPathComponent;
    }

    public File createDir(){

        File dataDir = new File(Environment.getExternalStorageDirectory()+ File.separator + PhotoActivity.getTimeStamp());
        dataDir.mkdir();

        File imageToCopy = new File (PhotoActivity.currentPhotoPath);
        File sourceLocation= dataDir;
        File targetLocation= imageToCopy;

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(sourceLocation);
            out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        addData(dataDir.getAbsolutePath());
        zipFileAtPath(dataDir.getAbsolutePath(), dataDir.getAbsolutePath()+".zip");
        return new File (dataDir.getAbsolutePath()+".zip");
		Toast FichierOk = Toast.makeText(getApplicationContext(), "Fichier créé", Toast.LENGTH_SHORT);
		toast.show();
    }

    public void addData(String path){
        Intent data = getIntent();
        HashMap<String, String> reponses = new HashMap<>();
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Set<String> keys = bundle.keySet();
                    Iterator<String> it = keys.iterator();
                    Log.e("RESULTS","Dumping Intent start");
                    while (it.hasNext()) {
                        String key = it.next();
                        Log.e("RESULTS","[" + key + "=" + bundle.get(key)+"]");
                        reponses.put(key, (String) bundle.get(key));
                    }
                    Log.e("RESULTS","Dumping Intent end");
                }
        try {
            FileWriter fw = new FileWriter(path + File.separator + "reponses.csv");
            String csv = TextUtils.join(";",reponses.keySet());
            fw.append(csv);
            csv=TextUtils.join(";",reponses.values());
            fw.append(csv);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		Toast CSVOk = Toast.makeText(getApplicationContext(), "CSV créé", Toast.LENGTH_SHORT);
		toast.show();
    }
}
   /*
   //Pour plus tard si on veut implémenter la possibilité pour l'utilisateur de consulter ses upload
   public boolean isExternalStorageWritable() {
   String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    //Checks if external storage is available to at least read

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    myContext.deleteFile(fileName);
    }*/



