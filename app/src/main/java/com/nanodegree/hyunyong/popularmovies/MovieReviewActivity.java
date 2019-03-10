package com.nanodegree.hyunyong.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nanodegree.hyunyong.popularmovies.data.NetworkUtils;
import com.nanodegree.hyunyong.popularmovies.data.Review;
import com.nanodegree.hyunyong.popularmovies.utilities.OpenMovieJsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieReviewActivity extends AppCompatActivity {

    private List<Review> mReviews = new ArrayList<>();
    private ReviewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int movieId = getIntent().getIntExtra(PopMoviesListActivity.MOVIE_ID, 0);

        setContentView(R.layout.activity_movie_review);

        RecyclerView recyclerView = findViewById(R.id.review_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ReviewAdapter(mReviews, new ReviewAdapter.ReviewAdapterOnClickHandler() {
            @Override
            public void onClick(String url) {
                Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                startActivity(browserIntent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        new FetchReviewData().execute(movieId);
    }

    private class FetchReviewData extends AsyncTask<Integer, Void, List<Review>> {

        @Override
        protected List<Review> doInBackground(Integer... integers) {
            URL popularMovieRequestUrl = NetworkUtils.buildReviewsMovieURL(integers[0]);
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(popularMovieRequestUrl);

                return OpenMovieJsonUtils
                        .getReviewFromJson(jsonMovieResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            if (reviews != null) {
                mReviews.addAll(reviews);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

}
