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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String AUTHORITY = "com.applicationsmobile.notesprovider";
    private static final String TABLE_NAME = "notesAM";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentResolver = getContentResolver();

        Button insertButton = findViewById(R.id.insert_button);
        Button queryButton = findViewById(R.id.query_button);
        Button updateButton = findViewById(R.id.update_button);
        Button deleteButton = findViewById(R.id.delete_button);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
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
                updateData();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void insertData() {
        ContentValues values = new ContentValues();
        values.put("nom", "Abed");
        values.put("prenom", "Moh");
        values.put("test", 12.5);
        values.put("examen", 10);
        values.put("moyenne", 11);

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

    private void updateData() {
        ContentValues values = new ContentValues();
        values.put("prenom", "Updated Name");

        int rowsUpdated = contentResolver.update(CONTENT_URI, values, "nom = ?", new String[]{"John"});

        Toast.makeText(this, "Rows Updated: " + rowsUpdated, Toast.LENGTH_SHORT).show();
    }

    private void deleteData() {
        int rowsDeleted = contentResolver.delete(CONTENT_URI, "nom = ?", new String[]{"John"});

        Toast.makeText(this, "Rows Deleted: " + rowsDeleted, Toast.LENGTH_SHORT).show();
    }
}
