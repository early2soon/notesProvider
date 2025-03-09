package com.applicationsmobile.notesprovider;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private static final String CONTENT_URI = "content://com.applicationsmobile.notesprovider/notesAM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableLayout);
        displayData();
    }

    private void displayData() {
        tableLayout.removeAllViews();

        TableRow headerRow = new TableRow(this);
        String[] headers = {"_id", "nom", "prenom", "test", "examen", "moyenne"};


        for (String columnName : headers) {

            TextView columnHeader = createTextView(columnName, true);

            headerRow.addView(columnHeader);
        }
        tableLayout.addView(headerRow);

        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI);
        Cursor cursor = resolver.query(uri, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                TableRow row = new TableRow(this);

                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String nom = cursor.getString(cursor.getColumnIndex("nom"));
                @SuppressLint("Range") String prenom = cursor.getString(cursor.getColumnIndex("prenom"));
                @SuppressLint("Range") String test = cursor.getString(cursor.getColumnIndex("test"));
                @SuppressLint("Range") String examen = cursor.getString(cursor.getColumnIndex("examen"));
                @SuppressLint("Range") String moyenne = cursor.getString(cursor.getColumnIndex("moyenne"));
                
                row.addView(createTextView(id, false));
                row.addView(createTextView(nom, false));
                row.addView(createTextView(prenom, false));
                row.addView(createTextView(test, false));
                row.addView(createTextView(examen, false));
                row.addView(createTextView(moyenne, false));

                tableLayout.addView(row);
            }
            cursor.close();
        }
    }

    private TextView createTextView(String text, boolean isHeader) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        if (isHeader) {
            textView.setBackgroundColor(Color.LTGRAY);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(16);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            textView.setTextSize(14);
        }
        return textView;
    }

    public void toggleRefreshBtn(View view) {
        displayData();
        Toast.makeText(this, "données actualisées", Toast.LENGTH_SHORT).show();
    }
}