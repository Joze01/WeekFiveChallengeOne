package com.applaudostudio.weekfivechallangeone.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.applaudostudio.weekfivechallangeone.loader.apiclient.GuardianApiClient;
import com.applaudostudio.weekfivechallangeone.receiver.InternetReceiver;
import com.applaudostudio.weekfivechallangeone.util.DataInterpreter;

import java.io.InputStream;
import java.net.URL;

/***
 * loader for the images of the detail
 */
public class LoaderBitMapsAsync extends AsyncTaskLoader<Bitmap> {
    private URL mUrlGenerated;

    /***
     * constructor
     * @param context context
     * @param generatedUrl URL object to be consult
     */
    public LoaderBitMapsAsync(Context context, URL generatedUrl) {
        super(context);
        mUrlGenerated = generatedUrl;
        onForceLoad();


    }

    /***
     * Load on background function to execute async work
     * @return a bit map
     */
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