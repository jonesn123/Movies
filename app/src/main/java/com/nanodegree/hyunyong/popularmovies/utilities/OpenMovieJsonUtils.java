package com.nanodegree.hyunyong.popularmovies.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Utility functions to handle Movie JSON data.
 */
public final class OpenMovieJsonUtils {

    public static String[] getImageURLStringsFromJson(Context context, String jsonStr)
            throws JSONException {

        final String MOV_MESSAGE_CODE = "cod";
        final String MOV_RESULT = "results";
        final String MOV_POSTER_PATH = "poster_path";
        String[] movieImagesData = null;

        JSONObject movieJson = new JSONObject(jsonStr);

        /* Is there an error? */
        if (movieJson.has(MOV_MESSAGE_CODE)) {
            int errorCode = movieJson.getInt(MOV_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }
        JSONArray movieArray = movieJson.getJSONArray(MOV_RESULT);
        movieImagesData = new String[movieArray.length()];

        for (int i = 0 ; i < movieArray.length(); i++) {
            JSONObject movie = movieArray.getJSONObject(i);
            String poster_path = movie.getString(MOV_POSTER_PATH);
            movieImagesData[i] = poster_path;
        }

        return movieImagesData;
    }
}
