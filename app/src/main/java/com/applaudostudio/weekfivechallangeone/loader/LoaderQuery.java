package com.applaudostudio.weekfivechallangeone.loader;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.applaudostudio.weekfivechallangeone.activity.MainActivity;
import com.applaudostudio.weekfivechallangeone.model.ItemNews;
import com.applaudostudio.weekfivechallangeone.persistence.contract.TheGuardianContact;

import java.net.URI;
import java.util.List;


public class LoaderQuery implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String KEY_BUNDLE_EXECUTE = "URI_ACTION";

    public static final String URI_ACTION_LOAD_NEWS = "LOAD_NEWS";
    public static final String URI_ACTION_LOAD_READ_ME_LATTER = "LOAD_READ_ME_LATTER";



    public List<ItemNews> localList;


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (args != null) {
            switch (args.getString(KEY_BUNDLE_EXECUTE)) {
                case URI_ACTION_LOAD_NEWS:
                    return new CursorLoader(null, TheGuardianContact.News.CONTENT_URI, null, null, null, null);
                case URI_ACTION_LOAD_READ_ME_LATTER:
                    return new CursorLoader(null, TheGuardianContact.News.CONTENT_URI, null, null, null, null);
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void setLocalList(Cursor cursor) {
        this.localList = localList;
    }

    public List<ItemNews> getLocalList() {
        return localList;
    }
}
