package com.nanodegree.hyunyong.popularmovies.data;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    // movie api base url
    private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";

    // image api base url
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    // image size
    public static final String IMAGE_SIZE_92 = "w92";
    public static final String IMAGE_SIZE_154 = "w154";
    public static final String IMAGE_SIZE_185 = "w185";
    public static final String IMAGE_SIZE_342 = "w342";
    public static final String IMAGE_SIZE_500 = "w500";
    public static final String IMAGE_SIZE_780 = "w780";
    public static final String IMAGE_SIZE_ORIGINAL = "original";

    // my api key in movie DB
    private static final String API_VALUE = "a0fd9d88f749bbc10d42589347a80e7a";

    // api path for movie restful api
    private final static String POP = "popular";
    private final static String REVIEWS = "reviews";
    private final static String VIDEOS = "videos";

    // query paraams
    private final static String API_KEY = "api_key";

    // http://api.themoviedb.org/3/movie/popular?api_key=a0fd9d88f749bbc10d42589347a80e7a
    public static URL buildPopularMovieURL() {

        Uri builtUri = Uri.parse(MOVIE_BASE_URL + POP).buildUpon()
                .appendQueryParameter(API_KEY, API_VALUE).build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    // http://api.themoviedb.org/3/movie/450465/reviews?api_key=a0fd9d88f749bbc10d42589347a80e7a
    public static URL buildReviewsMovieURL(int movieID) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL + movieID + "/" + REVIEWS).buildUpon()
                .appendQueryParameter(API_KEY, API_VALUE).build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    // http://api.themoviedb.org/3/movie/399579/videos?api_key=a0fd9d88f749bbc10d42589347a80e7a
    public static URL buildVideoMovieURL(int movieID) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL + movieID + "/" + VIDEOS).buildUpon()
                .appendQueryParameter(API_KEY, API_VALUE).build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getImageUrl(String imageSize185, String movieImageUrl) {
        return  IMAGE_BASE_URL + imageSize185 + movieImageUrl;
    }
}
