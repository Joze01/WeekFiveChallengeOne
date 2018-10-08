package com.applaudostudio.weekfivechallangeone.persistence.tablesmanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.applaudostudio.weekfivechallangeone.persistence.contract.TheGuardianContact;
import com.applaudostudio.weekfivechallangeone.persistence.helper.TheGuardianDbHelper;

public class ReadMeLatterTableManager {
    private TheGuardianDbHelper mHelperDb;
    private SQLiteDatabase mDb;

    public static final String CREATE_TABLE_READ_ME = "CREATE TABLE IF NOT EXISTS " + TheGuardianContact.ReadMeLatter.TABLE_NAME + " " +
            "(" + TheGuardianContact.ReadMeLatter.COLUMN_NEW_ID + " TEXT PRIMARY KEY, " +
            "" + TheGuardianContact.ReadMeLatter.COLUMN_NEW_HEAD_LINE + " TEXT NOT NULL, " +
            "" + TheGuardianContact.ReadMeLatter.COLUMN_NEW_BODY_TEXT + " TEXT, " +
            "" + TheGuardianContact.ReadMeLatter.COLUMN_NEW_WEB_URL + " TEXT, " +
            "" + TheGuardianContact.ReadMeLatter.COLUMN_NEW_THUMBNAIL + " TEXT," +
            "" + TheGuardianContact.ReadMeLatter.COLUMN_NEW_CATEGORY + " TEXT )";

    public ReadMeLatterTableManager(TheGuardianDbHelper dbHelper) {
        mHelperDb = dbHelper;
    }

    public boolean addNew(ContentValues values) {
        mDb = mHelperDb.getWritableDatabase();
        return mDb.insertWithOnConflict(TheGuardianContact.ReadMeLatter.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE) != -1;
    }

    public boolean deleteNews(Uri uri, String where, String[] selectionArgs) {
        mDb = mHelperDb.getWritableDatabase();
        boolean result= mDb.delete(TheGuardianContact.ReadMeLatter.TABLE_NAME, where, selectionArgs) != -1;
        return result;
    }

    public Cursor getNews(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        mDb = mHelperDb.getReadableDatabase();
        return mDb.query(TheGuardianContact.ReadMeLatter.TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
    }
}
