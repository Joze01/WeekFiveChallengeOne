package com.applaudostudio.weekfivechallangeone.persistence.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.applaudostudio.weekfivechallangeone.persistence.contract.TheGuardianContact;
import com.applaudostudio.weekfivechallangeone.persistence.helper.TheGuardianDbHelper;
import com.applaudostudio.weekfivechallangeone.persistence.tablesmanager.NewTableManager;
import com.applaudostudio.weekfivechallangeone.persistence.tablesmanager.ReadMeLatterTableManager;

public class NewsProvider extends ContentProvider {
    private static final int NEWS = 1000;
    private static final int READ_ME = 2000;
    private static final int READ_ME_ID = 2001;
    private TheGuardianDbHelper dbHelper;
    private int mMatch;
    private NewTableManager mTableNews;
    private ReadMeLatterTableManager mTableReadme;

    public NewsProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
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
    public String getType(Uri uri) {
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
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

    @Override
    public boolean onCreate() {
        dbHelper = new TheGuardianDbHelper(getContext());
        mTableNews = new NewTableManager(dbHelper);
        mTableReadme = new ReadMeLatterTableManager(dbHelper);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
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
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(String.valueOf(TheGuardianContact.CONTENT_AUTHORITY), "news", NEWS);
        sURIMatcher.addURI(String.valueOf(TheGuardianContact.CONTENT_AUTHORITY), "readme", READ_ME);
    }

}
