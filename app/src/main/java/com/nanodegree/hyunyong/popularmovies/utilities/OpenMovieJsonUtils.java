package com.nanodegree.hyunyong.popularmovies.utilities;

import com.nanodegree.hyunyong.popularmovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions to handle Movie JSON data.
 */
public final class OpenMovieJsonUtils {

    public static List<Movie> getImageURLStringsFromJson(String jsonStr)
            throws JSONException {

        final String MOV_MESSAGE_CODE = "cod";
        final String MOV_RESULT = "results";

        final String VOTE_COUNT = "vote_count";
        final String ID = "id";
        final String VIDEO = "video";
        final String VOTE_AVERAGE = "vote_average";
        final String TITLE = "title";
        final String POPULARITY = "popularity";
        final String MOV_POSTER_PATH = "poster_path";
        final String ORIGINAL_LANGUAGE = "original_language";
        final String ORIGINAL_TITLE = "original_title";
        final String BACKDROP_PATH = "backdrop_path";
        final String ADULT = "adult";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";

        List<Movie> movies = new ArrayList<>();

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
        for (int i = 0 ; i < movieArray.length(); i++) {
            JSONObject jsonMovie = movieArray.getJSONObject(i);
            int vote_count = jsonMovie.getInt(VOTE_COUNT);
            int id = jsonMovie.getInt(ID);
            boolean video = jsonMovie.getBoolean(VIDEO);
            double vote_average = jsonMovie.getDouble(VOTE_AVERAGE);
            String title = jsonMovie.getString(TITLE);
            double popularity = jsonMovie.getDouble(POPULARITY);
            String poster_path = jsonMovie.getString(MOV_POSTER_PATH);
            String original_language = jsonMovie.getString(ORIGINAL_LANGUAGE);
            String original_title = jsonMovie.getString(ORIGINAL_TITLE);
            String backdrop_path = jsonMovie.getString(BACKDROP_PATH);
            boolean adult = jsonMovie.getBoolean(ADULT);
            String overview = jsonMovie.getString(OVERVIEW);
            String release_date = jsonMovie.getString(RELEASE_DATE);

            Movie movie = new Movie(vote_count, id, video, vote_average,
                    title, popularity, poster_path, original_language, original_title,
                    backdrop_path, adult, overview, release_date);

            movies.add(movie);

        }

        return movies;
    }
}
