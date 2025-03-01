package com.applicationsmobile.notesprovider.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.applicationsmobile.notesprovider.database.notesDbSchema.notesTable;

public class NotesBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "notesDB.db";
    public NotesBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + notesTable.NAME +  "(" +
                " _id integer primary key autoincrement, " +  // Unique ID column
                notesTable.Cols.NOM + " TEXT, " +  // Column for 'nom'
                notesTable.Cols.PRENOM + " TEXT, " + // Column for 'prenom'
                notesTable.Cols.TEST + " REAL, " + // Column for test scores
                notesTable.Cols.EXAMEN + " REAL, " + // Column for exam scores
                notesTable.Cols.MOYENNE + " REAL" + // Column for average score
                ")");
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + notesTable.NAME);
        onCreate(db); // Recreate the table
    }
}
