package com.applaudostudio.weekfivechallangeone.loader;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.applaudostudio.weekfivechallangeone.loader.apiclient.*;
import com.applaudostudio.weekfivechallangeone.util.DataInterpreter;

import java.io.InputStream;
import java.net.URL;


public class LoaderNewsAsync extends AsyncTaskLoader<String> {
    private URL mUrlGenerated;

    /***
     * constructor
     * @param context context
     * @param generatedUrl URL object to be consult
     */
    public LoaderNewsAsync(Context context, URL generatedUrl) {
        super(context);
        mUrlGenerated = generatedUrl;
        onForceLoad();
    }

    /***
     * Load on background function to execute async work
     * @return a string
     */
    @Nullable
    @Override
    public String loadInBackground() {
        GuardianApiClient mApiClient;
        DataInterpreter inputStreamInterpreter = new DataInterpreter();
        mApiClient = new GuardianApiClient();
        InputStream downloadedData = mApiClient.makeHttpRequest(mUrlGenerated);
        return inputStreamInterpreter.streamToString(downloadedData);
    }
}