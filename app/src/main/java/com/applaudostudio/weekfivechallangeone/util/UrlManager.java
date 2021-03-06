package com.applaudostudio.weekfivechallangeone.util;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlManager {
    public static final int ELEMENT_TYPE_JSON = 1;
    public static final int ELEMENT_TYPE_IMAGE = 2;


    /***
     * Generate  a URL() for the Recycler view, including Pagination and for the images
     * @param elementType  Type JSON or IMAGE
     * @param paramURL string url
     * @param page page param for the JSON
     * @param firstLoad if the JSON is on firt load
     * @return returns URL object
     */
    public URL GenerateURLByElement(int elementType, String paramURL, int page, boolean firstLoad) {
        String urlBase = "";
        switch (elementType) {
            case ELEMENT_TYPE_JSON:
                if (firstLoad) {
                    urlBase = "https://content.guardianapis.com/search?show-fields=all&page=1&page-size=30&q=" + paramURL + "&api-key=74738db0-7091-4ca8-8abb-440a0f56643e";
                } else {
                    if (page > 6) {
                        urlBase = "https://content.guardianapis.com/search?show-fields=all&page=" + page + "&page-size=5&q=" + paramURL + "&api-key=74738db0-7091-4ca8-8abb-440a0f56643e";
                    } else {
                        urlBase = "https://content.guardianapis.com/search?show-fields=all&page=7&page-size=5&q=" + paramURL + "&api-key=74738db0-7091-4ca8-8abb-440a0f56643e";
                    }
                }
                break;
            case ELEMENT_TYPE_IMAGE:
                urlBase = paramURL;
                break;
        }
        URL url;
        try {
            url = new URL(urlBase);
        } catch (MalformedURLException exception) {
            Log.e("LOG", "Error with creating URL:" + urlBase, exception);
            return null;
        }
        return url;
    }


}
