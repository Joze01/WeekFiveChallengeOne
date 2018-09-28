package com.applaudostudio.weekfivechallangeone.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.applaudostudio.weekfivechallangeone.loader.apiclient.GuardianApiClient;
import com.applaudostudio.weekfivechallangeone.util.DataInterpreter;

import java.io.InputStream;
import java.net.URL;


public class LoaderBitMapsAsync extends AsyncTaskLoader<Bitmap> {
    private URL mUrlGenerated;

    public LoaderBitMapsAsync(Context context, URL generatedUrl) {
        super(context);
        mUrlGenerated = generatedUrl;
        onForceLoad();
    }

    @Nullable
    @Override
    public Bitmap loadInBackground() {
        GuardianApiClient mApiClient;
        DataInterpreter inputStreamInterpreter = new DataInterpreter();
        mApiClient = new GuardianApiClient();
        InputStream downloadedData = mApiClient.makeHttpRequest(mUrlGenerated);
        return inputStreamInterpreter.streamToBitMap(downloadedData);
    }
}