package com.applaudostudio.weekfivechallangeone.persistence.tablesmanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.applaudostudio.weekfivechallangeone.persistence.contract.TheGuardianContact;
import com.applaudostudio.weekfivechallangeone.persistence.helper.TheGuardianDbHelper;

public class ReadMeLatterTableManager {
    private TheGuardianDbHelper mHelperDb;
    private SQLiteDatabase mDb;

    public static final String CREATE_TABLE_READ_ME = "CREATE TABLE " + TheGuardianContact.ReadMeLatter.TABLE_NAME + " " +
            "(" + TheGuardianContact.News.COLUMN_NEW_ID + " TEXT PRIMARY KEY, " +
            "" + TheGuardianContact.News.COLUMN_NEW_HEAD_LINE + " TEXT NOT NULL, " +
            "" + TheGuardianContact.News.COLUMN_NEW_BODY_TEXT + " TEXT, " +
            "" + TheGuardianContact.News.COLUMN_NEW_WEB_URL + " TEXT, " +
            "" + TheGuardianContact.News.COLUMN_NEW_THUMBNAIL + "TEXT," +
            "" + TheGuardianContact.News.COLUMN_NEW_CATEGORY + "TEXT )";

    public ReadMeLatterTableManager(TheGuardianDbHelper dbHelper) {
        mHelperDb = dbHelper;
    }


    public boolean addNew(ContentValues values) {
       /* ContentValues contentValues = new ContentValues();
        contentValues.put(TheGuardianContact.ReadMeLatter.COLUMN_NEW_ID, item.getNewId());
        contentValues.put(TheGuardianContact.ReadMeLatter.COLUMN_NEW_HEAD_LINE, item.getTitle());
        contentValues.put(TheGuardianContact.ReadMeLatter.COLUMN_NEW_BODY_TEXT, item.getTextBody());
        contentValues.put(TheGuardianContact.ReadMeLatter.COLUMN_NEW_WEB_URL, item.getWebUrl());
        contentValues.put(TheGuardianContact.ReadMeLatter.COLUMN_NEW_THUMBNAIL, item.getWebUrl());
        */
        mDb = mHelperDb.getWritableDatabase();
        return mDb.insert(TheGuardianContact.ReadMeLatter.TABLE_NAME, null, values) != -1;
    }

    boolean deleteNew(String id) {
        mDb = mHelperDb.getWritableDatabase();
        return mDb.delete(TheGuardianContact.ReadMeLatter.TABLE_NAME, TheGuardianContact.News.COLUMN_NEW_ID + "=?", new String[]{id}) == 1;
    }

    public Cursor getAllNews() {
        mDb = mHelperDb.getReadableDatabase();
        return mDb.rawQuery("SELECT * FROM " + TheGuardianContact.ReadMeLatter.TABLE_NAME, null);
    }

    public Cursor getANews(String id) {
        mDb = mHelperDb.getReadableDatabase();
        return mDb.rawQuery("SELECT * FROM " + TheGuardianContact.ReadMeLatter.TABLE_NAME + "WHERE " + TheGuardianContact.ReadMeLatter.COLUMN_NEW_ID + "=" + id, null);
    }
}
