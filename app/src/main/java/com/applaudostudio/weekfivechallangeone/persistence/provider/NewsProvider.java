package com.applaudostudio.weekfivechallangeone.persistence.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.applaudostudio.weekfivechallangeone.persistence.contract.TheGuardianContact;
import com.applaudostudio.weekfivechallangeone.persistence.helper.TheGuardianDbHelper;
import com.applaudostudio.weekfivechallangeone.persistence.tablesmanager.NewTableManager;
import com.applaudostudio.weekfivechallangeone.persistence.tablesmanager.ReadMeLatterTableManager;

/***
 * Content provider for the SQLite database
 */
public class NewsProvider extends ContentProvider {
    private static final int NEWS = 1000;
    private static final int READ_ME = 2000;
    private int mMatch;
    private NewTableManager mTableNews;
    private ReadMeLatterTableManager mTableReadme;

    public NewsProvider() {
    }

    /***
     * delete functions of the database
     * @param uri uri for the tables
     * @param selection selection arguments
     * @param selectionArgs and selection values to search
     * @return return an int
     */
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        mMatch = sURIMatcher.match(uri);
        switch (mMatch) {
            case NEWS:
                mTableNews.deleteNews(uri, selection, selectionArgs);
                break;
            case READ_ME:
                mTableReadme.deleteNews(uri, selection, selectionArgs);
                break;
        }
        return 1;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /***
     * Function to insert int the data base for insert each table
     * @param uri uri to check the table
     * @param values values to insert
     * @return null uri
     */
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        mMatch = sURIMatcher.match(uri);
        switch (mMatch) {
            case NEWS:
                mTableNews.addNew(values);
                break;
            case READ_ME:
                mTableReadme.addNew(values);
                break;
        }
        return null;
    }

    /***
     * create to setup the connection and the controllers for each table
     * @return returns true
     */
    @Override
    public boolean onCreate() {
        TheGuardianDbHelper dbHelper;
        dbHelper = new TheGuardianDbHelper(getContext());
        mTableNews = new NewTableManager(dbHelper);
        mTableReadme = new ReadMeLatterTableManager(dbHelper);
        return true;
    }

    /***
     * function to execute selects for the sqllite db
     * @param uri uri to get the table
     * @param projection colums for the query, (NULL) is *
     * @param selection where params
     * @param selectionArgs arguments for the where
     * @param sortOrder order of the results
     * @return a cursor
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        mMatch = sURIMatcher.match(uri);
        switch (mMatch) {
            case NEWS:
                return mTableNews.getNews(projection, selection, selectionArgs, sortOrder);
            case READ_ME:
                return mTableReadme.getNews(projection, selection, selectionArgs, sortOrder);
        }
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Static function to get the data to match the URI
     */
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(String.valueOf(TheGuardianContact.CONTENT_AUTHORITY), "news", NEWS);
        sURIMatcher.addURI(String.valueOf(TheGuardianContact.CONTENT_AUTHORITY), "readme", READ_ME);
    }

}
