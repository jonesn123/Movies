package com.nanodegree.hyunyong.popularmovies.utilities;

import com.nanodegree.hyunyong.popularmovies.data.Movie;
import com.nanodegree.hyunyong.popularmovies.data.Review;
import com.nanodegree.hyunyong.popularmovies.data.Video;

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

    public static List<Video> getVideoURLStringsFromJson(String jsonStr)
            throws JSONException {
        final String ID = "id";
        final String RESULTS = "results";
        final String ISO_639_1 = "iso_639_1";
        final String ISO_3166_1 = "iso_3166_1";
        final String KEY = "key";
        final String NAME = "name";
        final String SITE = "site";
        final String SIZE = "size";
        final String TYPE = "type";

        List<Video> videos = new ArrayList<>();

        JSONObject videoJson = new JSONObject(jsonStr);
        /* Is there an error? */
        JSONArray movieArray = videoJson.getJSONArray(RESULTS);
        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject jsonVideo = movieArray.getJSONObject(i);
            String id = jsonVideo.getString(ID);
            String iso_639_1 = jsonVideo.getString(ISO_639_1);
            String iso_3166_1 = jsonVideo.getString(ISO_3166_1);
            String key = jsonVideo.getString(KEY);
            String name = jsonVideo.getString(NAME);
            String site = jsonVideo.getString(SITE);
            int size = jsonVideo.getInt(SIZE);
            String type = jsonVideo.getString(TYPE);

            Video video = new Video(id, iso_639_1, iso_3166_1, key, name, site, size, type);

            videos.add(video);

        }

        return videos;

    }

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
        for (int i = 0; i < movieArray.length(); i++) {
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

    public static List<Review> getReviewFromJson(String jsonStr)
            throws JSONException {

        final String RESULT = "results";

        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String ID = "id";
        final String URL = "url";

        List<Review> reviews = new ArrayList<>();

        JSONObject reviewJson = new JSONObject(jsonStr);

        JSONArray reviewArray = reviewJson.getJSONArray(RESULT);
        for (int i = 0; i < reviewArray.length(); i++) {
            JSONObject jsonReview = reviewArray.getJSONObject(i);
            String author = jsonReview.getString(AUTHOR);
            String content = jsonReview.getString(CONTENT);
            String id = jsonReview.getString(ID);
            String url = jsonReview.getString(URL);
            Review review = new Review(author, content, id, url);

            reviews.add(review);

        }

        return reviews;
    }
}
