package com.applaudostudio.weekfivechallangeone.util;

import android.util.Log;

import com.applaudostudio.weekfivechallangeone.model.ItemNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParserItem {

    /***
     * Function to get a list of News model from a JSON result
     * @param jsonString STRING JSON
     * @return returns a list<NewItem>
     */
    public List<ItemNews> getNewList(String jsonString, String category) {
        List<ItemNews> resultList = new ArrayList<>();
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
                        itemNews.setTitle(properties.getString("headline"));
                    if (properties.has("bodyText"))
                        itemNews.setTextBody(properties.getString("bodyText"));
                    if (properties.has("thumbnail"))
                        itemNews.setThumbnailUrl(properties.getString("thumbnail"));
                    if (firstFeature.has("webUrl"))
                        itemNews.setWebUrl(firstFeature.getString("webUrl"));
                    if (firstFeature.has("id"))
                        itemNews.setNewId(firstFeature.getString("id"));
                    itemNews.setCategory(category);
                    resultList.add(itemNews);
                }
            }
        } catch (JSONException e) {
            Log.e("ERROR_PARSING", "Problem parsing the ITEM JSON results", e);
        }
        return resultList;
    }


}
