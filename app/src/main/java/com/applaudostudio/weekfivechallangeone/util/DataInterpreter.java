package com.applaudostudio.weekfivechallangeone.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class DataInterpreter {
    /***
     * input stream to get string format of it
     * @param inputStream input stream downloaded
     * @return a string result(JSON)
     */
    public String streamToString(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            try {
                line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output.toString();
    }


    /***
     * Input stream to bit map function
     * @param input input stream
     * @return returns a bitmap
     */
    public Bitmap streamToBitMap(InputStream input) {
        return BitmapFactory.decodeStream(input);
    }
}
