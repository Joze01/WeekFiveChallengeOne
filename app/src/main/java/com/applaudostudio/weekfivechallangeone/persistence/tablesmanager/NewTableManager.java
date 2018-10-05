package com.applaudostudio.weekfivechallangeone.persistence.tablesmanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

import com.applaudostudio.weekfivechallangeone.persistence.contract.TheGuardianContact;
import com.applaudostudio.weekfivechallangeone.persistence.helper.TheGuardianDbHelper;

public class NewTableManager {
    private TheGuardianDbHelper mHelperDb;
    private SQLiteDatabase mDb;


    public static final String CREATE_TABLE_NEWS = "CREATE TABLE IF NOT EXISTS " + TheGuardianContact.News.TABLE_NAME + " " +
            "(" + TheGuardianContact.News.COLUMN_NEW_ID + " TEXT PRIMARY KEY, " +
            "" + TheGuardianContact.News.COLUMN_NEW_HEAD_LINE + " TEXT NOT NULL, " +
            "" + TheGuardianContact.News.COLUMN_NEW_BODY_TEXT + " TEXT, " +
            "" + TheGuardianContact.News.COLUMN_NEW_WEB_URL + " TEXT, " +
            "" + TheGuardianContact.News.COLUMN_NEW_THUMBNAIL + " TEXT," +
            "" + TheGuardianContact.News.COLUMN_NEW_CATEGORY + " TEXT )";

    public NewTableManager(TheGuardianDbHelper helperDb) {
        mHelperDb = helperDb;
    }

    public boolean addNew(ContentValues values) {
        mDb = mHelperDb.getWritableDatabase();
        return mDb.insertWithOnConflict(TheGuardianContact.News.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE) != -1;
    }

    public boolean deleteNews(Uri uri, String where, String[] selectionArgs) {
        mDb = mHelperDb.getWritableDatabase();
        return mDb.delete(TheGuardianContact.News.TABLE_NAME, where, selectionArgs) != -1;
    }

    public Cursor getAllNews(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        mDb = mHelperDb.getReadableDatabase();
        return mDb.query(TheGuardianContact.News.TABLE_NAME, projection, selection, selectionArgs, sortOrder,null,null);
    }


}
