package com.applicationsmobile.notesprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.applicationsmobile.notesprovider.database.NotesBaseHelper;

import java.util.ArrayList;

public class openNotes {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private openNotes(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new NotesBaseHelper(mContext)
                .getWritableDatabase();
        ArrayList<Object> mNotes = new ArrayList<>();
    }
}
