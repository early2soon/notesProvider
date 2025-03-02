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

        /* int id = idET.getText().toString().isEmpty() ? 0 : Integer.parseInt(idET.getText().toString());
        String nom = nomET.getText().toString().isEmpty() ? "default_nom" : nomET.getText().toString();
        String prenom = prenomET.getText().toString().isEmpty() ? "default_prenom" : prenomET.getText().toString();
        float test = testET.getText().toString().isEmpty() ? 0 : Float.parseFloat(testET.getText().toString());
        float examen = examenET.getText().toString().isEmpty() ? 0 : Float.parseFloat(examenET.getText().toString());
        float moyenne = moyenneET.getText().toString().isEmpty() ? 0 : Float.parseFloat(moyenneET.getText().toString()); */


        Button insertButton = findViewById(R.id.insert_button);
        Button queryButton = findViewById(R.id.query_button);
        Button updateButton = findViewById(R.id.update_button);
        Button deleteButton = findViewById(R.id.delete_button);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!idET.getText().toString().trim().isEmpty()) {
                    // int id = Integer.parseInt(idET.getText().toString().trim());
                    String nom = nomET.getText().toString().trim();
                    String prenom = prenomET.getText().toString().trim();
                    float test = testET.getText().toString().isEmpty() ? 0 : Float.parseFloat(testET.getText().toString());
                    float examen = examenET.getText().toString().isEmpty() ? 0 : Float.parseFloat(examenET.getText().toString());
                    float moyenne = moyenneET.getText().toString().isEmpty() ? 0 : Float.parseFloat(moyenneET.getText().toString());

                    insertData(nom, prenom, test, examen, moyenne);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a valid ID!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!idET.getText().toString().isEmpty()) {
                    int id = Integer.parseInt(idET.getText().toString());
                    String nom = nomET.getText().toString();
                    String prenom = prenomET.getText().toString();
                    String testText = testET.getText().toString();
                    float test = testText.isEmpty() ? 0 : Float.parseFloat(testText);
                    String examenText = examenET.getText().toString();
                    float examen = examenText.isEmpty() ? 0 : Float.parseFloat(examenText);
                    String moyenneText = moyenneET.getText().toString();
                    float moyenne = moyenneText.isEmpty() ? 0 : Float.parseFloat(moyenneText);

                    queryData();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a valid ID!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!idET.getText().toString().trim().isEmpty()) {
                    try {
                        int id = Integer.parseInt(idET.getText().toString().trim());
                        String nom = nomET.getText().toString().trim();
                        String prenom = prenomET.getText().toString().trim();
                        float test = testET.getText().toString().isEmpty() ? 0 : Float.parseFloat(testET.getText().toString());
                        float examen = examenET.getText().toString().isEmpty() ? 0 : Float.parseFloat(examenET.getText().toString());
                        float moyenne = moyenneET.getText().toString().isEmpty() ? 0 : Float.parseFloat(moyenneET.getText().toString());

                        updateData(id, nom, prenom, test, examen, moyenne);
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Invalid ID!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a valid ID!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!idET.getText().toString().isEmpty()) {
                    int id = Integer.parseInt(idET.getText().toString());
                    deleteData();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a valid ID!", Toast.LENGTH_SHORT).show();
                }
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
                @SuppressLint("Range") double test = cursor.getDouble(cursor.getColumnIndex("test"));
                @SuppressLint("Range") double examen = cursor.getDouble(cursor.getColumnIndex("examen"));
                @SuppressLint("Range") double moyenne = cursor.getDouble(cursor.getColumnIndex("moyenne"));

                /* result.append("ID: ").append(id)
                        .append(", Nom: ").append(nom)
                        .append(", Prenom: ").append(prenom)
                        .append(", Test: ").append(test)
                        .append(", Examen: ").append(examen)
                        .append(", Moyenne: ").append(moyenne)
                        .append("\n"); */
                idET.setText(String.valueOf(id));
                nomET.setText(nom);
                prenomET.setText(prenom);
                testET.setText(String.valueOf(test));
                examenET.setText(String.valueOf(examen));
                moyenneET.setText(String.valueOf(moyenne));
            }
            cursor.close();
            Toast.makeText(this, "Results found!", Toast.LENGTH_LONG).show();
            //Log.d(TAG, "Query Result:\n" + result.toString());
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
                "_id = ?",
                new String[]{String.valueOf(id)}
        );

        Toast.makeText(this, "Rows Updated: " + rowsUpdated, Toast.LENGTH_SHORT).show();
    }

    private void deleteData() {
        if (!idET.getText().toString().trim().isEmpty()) {
            int id = Integer.parseInt(idET.getText().toString().trim());
            int rowsDeleted = contentResolver.delete(CONTENT_URI, "_id = ?", new String[]{String.valueOf(id)});
            Toast.makeText(this, "Rows Deleted: " + rowsDeleted, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Deletion failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
