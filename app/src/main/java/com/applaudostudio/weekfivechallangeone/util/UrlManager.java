package com.applaudostudio.weekfivechallangeone.util;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlManager {
    public static final int ELEMENT_TYPE_JSON=1;
    public static final int ELEMENT_TYPE_IMAGE=2;



    /**
     * Returns new URL object from the given string URL.
     */
    public URL createFirstLoadUrl(String category) {
        String urlBase="https://content.guardianapis.com/search?show-fields=all&page=1&page-size=30&q="+category+"&api-key=74738db0-7091-4ca8-8abb-440a0f56643e";
        URL url = null;

        try {
            url = new URL(urlBase);
        } catch (MalformedURLException exception) {
            Log.e("LOG", "Error with creating URL", exception);
            return null;
        }
        return url;
    }


    public URL GenerateURLByElement(int elementType,String paramURL, int page, boolean firstLoad){
        String urlBase="";
        switch (elementType){
            case ELEMENT_TYPE_JSON:
                if(firstLoad){
                    urlBase="https://content.guardianapis.com/search?show-fields=all&page=1&page-size=30&q="+paramURL+"&api-key=74738db0-7091-4ca8-8abb-440a0f56643e";
                }else{
                    if(page>6) {
                        urlBase = "https://content.guardianapis.com/search?show-fields=all&page=" + page + "&page-size=5&q=" + paramURL + "&api-key=74738db0-7091-4ca8-8abb-440a0f56643e";
                    }else{
                        urlBase = "https://content.guardianapis.com/search?show-fields=all&page=7&page-size=5&q=" + paramURL + "&api-key=74738db0-7091-4ca8-8abb-440a0f56643e";
                    }
                }
                break;
            case ELEMENT_TYPE_IMAGE:
                urlBase=paramURL;
                break;

        }
        URL url = null;
        try {
            url = new URL(urlBase);
        } catch (MalformedURLException exception) {
            Log.e("LOG", "Error with creating URL", exception);
            return null;
        }
        return url;

    }


}
