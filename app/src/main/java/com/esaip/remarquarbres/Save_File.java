package com.esaip.remarquarbres;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Save_File extends AppCompatActivity {
private Button create;
private Button send;
private Context context;
private String urlServeur="https://requestbin.fullcontact.com/1mbiedi1?inspect";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save__file);

        create = (Button) findViewById(R.id.create_file);
        send = (Button) findViewById(R.id.send);
//Pour l'instant les fichiers sont montés sur le stockage interne de l'application (inaccessible par le reste du téléphone)
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "hello.txt";
                String content = "hello world";

                FileOutputStream outputStream = null;
                try {
                    outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    outputStream.write(content.getBytes());
                    outputStream.close();

                    Context contexte = getApplicationContext();
                    CharSequence text = "Succès!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(contexte, text, duration);
                    toast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
    }
    public String getLastPathComponent(String filePath) {
        String[] segments = filePath.split("/");
        if (segments.length == 0)
            return "";
        String lastPathComponent = segments[segments.length - 1];
        return lastPathComponent;
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


}
