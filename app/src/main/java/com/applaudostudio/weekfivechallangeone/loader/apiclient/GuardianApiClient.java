package com.applaudostudio.weekfivechallangeone.loader.apiclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GuardianApiClient {
    /***
     * Make
     * @param url HTTP URI for the end point
     * @return
     */
    public InputStream makeHttpRequest(URL url) {
        InputStream inputStream = null;
        if (url == null) {
            return inputStream;
        }
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                return inputStream;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }

}
