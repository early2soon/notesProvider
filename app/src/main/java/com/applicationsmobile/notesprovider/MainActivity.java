package com.applicationsmobile.notesprovider;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String AUTHORITY = "com.applicationsmobile.notesprovider";
    private static final String TABLE_NAME = "notesAM";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);


    EditText idET, nomET, prenomET, testET, examenET, moyenneET;


    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentResolver = getContentResolver();

        idET = (EditText) findViewById(R.id.idET);
        nomET = (EditText) findViewById(R.id.nomET);
        prenomET = (EditText) findViewById(R.id.prenomET);
        testET = (EditText) findViewById(R.id.testET);
        examenET = (EditText) findViewById(R.id.examenET);
        moyenneET = (EditText) findViewById(R.id.moyenneET);


        int id = Integer.parseInt(idET.getText().toString());
        String nom = nomET.getText().toString();
        String prenom = prenomET.getText().toString();
        float test = Float.parseFloat(testET.getText().toString());
        float examen = Float.parseFloat(examenET.getText().toString());
        float moyenne = Float.parseFloat(moyenneET.getText().toString());

        Button insertButton = findViewById(R.id.insert_button);
        Button queryButton = findViewById(R.id.query_button);
        Button updateButton = findViewById(R.id.update_button);
        Button deleteButton = findViewById(R.id.delete_button);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(nom, prenom, test, examen, moyenne);
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryData();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(id, nom, prenom, test, examen, moyenne);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(id);
            }
        });
    }

    private void insertData(String nom, String prenom, float test, float examen, float moyenne) {

        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("prenom", prenom);
        values.put("test", test);
        values.put("examen", examen);
        values.put("moyenne", moyenne);

        Uri resultUri = contentResolver.insert(CONTENT_URI, values);

        if (resultUri != null) {
            Toast.makeText(this, "Data Inserted: " + resultUri.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Insert Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void queryData() {
        Cursor cursor = contentResolver.query(CONTENT_URI, new String[]{"_id", "nom", "prenom", "test", "examen", "moyenne"}, null, null, "nom ASC");

        if (cursor != null) {
            StringBuilder result = new StringBuilder();
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String nom = cursor.getString(cursor.getColumnIndex("nom"));
                @SuppressLint("Range") String prenom = cursor.getString(cursor.getColumnIndex("prenom"));
                @SuppressLint("Range") int test = cursor.getInt(cursor.getColumnIndex("test"));
                @SuppressLint("Range") int examen = cursor.getInt(cursor.getColumnIndex("examen"));
                @SuppressLint("Range") double moyenne = cursor.getDouble(cursor.getColumnIndex("moyenne"));

                result.append("ID: ").append(id)
                        .append(", Nom: ").append(nom)
                        .append(", Prenom: ").append(prenom)
                        .append(", Test: ").append(test)
                        .append(", Examen: ").append(examen)
                        .append(", Moyenne: ").append(moyenne)
                        .append("\n");
            }
            cursor.close();
            Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
            Log.d(TAG, "Query Result:\n" + result.toString());
        } else {
            Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData(int id, String newNom, String newPrenom, float newTest, float newExamen, float newMoyenne) {
        ContentValues values = new ContentValues();

        values.put("nom", newNom);
        values.put("prenom", newPrenom);
        values.put("test", newTest);
        values.put("examen", newExamen);
        values.put("moyenne", newMoyenne);

        int rowsUpdated = getContentResolver().update(
                CONTENT_URI,  // Your Content Provider URI
                values,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        Toast.makeText(this, "Rows Updated: " + rowsUpdated, Toast.LENGTH_SHORT).show();
    }

    private void deleteData(int id) {
        int rowsDeleted = contentResolver.delete(CONTENT_URI, "id = ?", new String[]{String.valueOf(id)});

        Toast.makeText(this, "Rows Deleted: " + rowsDeleted, Toast.LENGTH_SHORT).show();
    }
}
