package com.applaudostudio.weekfivechallangeone.persistence.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.applaudostudio.weekfivechallangeone.persistence.contract.TheGuardianContact;
import com.applaudostudio.weekfivechallangeone.persistence.tablesmanager.NewTableManager;
import com.applaudostudio.weekfivechallangeone.persistence.tablesmanager.ReadMeLatterTableManager;

/***
 * Open helper class for the database
 */
public class TheGuardianDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_PATH = "/databases/";
    private static final String DATABASE_NAME = "theguardian.db";
    private static final int DATABASE_VERSION = 1;

    /***
     * Constructor
     * @param context context as param
     */
    public TheGuardianDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /***
     * On create db call the statements of the each table.
     * @param db database param
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NewTableManager.CREATE_TABLE_NEWS);
        db.execSQL(ReadMeLatterTableManager.CREATE_TABLE_READ_ME);
    }

    /***
     * on upgrade db call the statement to delete all the old data and recreate a new tables
     * @param db db param
     * @param oldVersion int for the old version of the db
     * @param newVersion int for the new version of the db
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TheGuardianContact.News.TABLE_NAME + ";";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS " + TheGuardianContact.ReadMeLatter.TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }
}
