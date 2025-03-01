package com.applicationsmobile.notesprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applicationsmobile.notesprovider.database.NotesBaseHelper;

import java.util.List;

public class notesContentProvider extends ContentProvider {

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        NotesBaseHelper dbHelper = new NotesBaseHelper(getContext());
        db = dbHelper.getReadableDatabase();  // Opens the database
        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String id = null;
        List<String> pathSegs = uri.getPathSegments();

        if (pathSegs.size() > 1) {
            id = pathSegs.get(pathSegs.size() - 1);
            if (selection == null) {
                selection = "_id = ?";
                selectionArgs = new String[]{id};
            } else {
                selection += " AND _id = ?";
                String[] temp = new String[selectionArgs.length + 1];
                System.arraycopy(selectionArgs, 0, temp, 0, selectionArgs.length);
                temp[temp.length - 1] = id;
                selectionArgs = temp;
            }
        }

        String tableName = pathSegs.get(0); // First part of URI is table name

        Cursor cursor = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/vnd.com.applicationsmobile.notesprovider";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = uri.getPathSegments().get(0);
        long id = db.insert(tableName, null, values);
        if (id != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.withAppendedPath(uri, String.valueOf(id));
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = uri.getPathSegments().get(0);
        int rowsDeleted = db.delete(tableName, selection, selectionArgs);
        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = uri.getPathSegments().get(0);
        int rowsUpdated = db.update(tableName, values, selection, selectionArgs);
        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
