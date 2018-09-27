package com.applaudostudio.weekfivechallangeone.util;

import android.util.Log;

import com.applaudostudio.weekfivechallangeone.model.ItemNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParserItem {
    public List<ItemNews> getNewList(String jsonString) {
        List<ItemNews> resultlList = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonString);
            JSONObject responseObject = baseJsonResponse.getJSONObject("response");
            JSONArray featureArray = responseObject.getJSONArray("results");
            // If there are results in the features array
            if (featureArray.length() > 0) {
                for (int i = 0; i < featureArray.length(); i++) {
                    ItemNews itemNews = new ItemNews();
                    JSONObject firstFeature = featureArray.getJSONObject(i);
                    JSONObject properties = firstFeature.getJSONObject("fields");
                    if (properties.has("headline"))
                        itemNews.setmTitle(properties.getString("headline"));
                    if (properties.has("bodyText"))
                        itemNews.setmTextBody(properties.getString("bodyText"));
                    if (properties.has("thumbnail"))
                        itemNews.setmThumbnailUrl(properties.getString("thumbnail"));
                    if (properties.has("webUrl"))
                        itemNews.setmWebUrl(firstFeature.getString("webUrl"));
                    resultlList.add(itemNews);
                }
            }
        } catch (JSONException e) {
            Log.e("ERROR_PARSING", "Problem parsing the ITEM JSON results", e);
        }

        return resultlList;
    }


}
