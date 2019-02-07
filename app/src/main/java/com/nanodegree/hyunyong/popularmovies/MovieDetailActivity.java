package com.nanodegree.hyunyong.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nanodegree.hyunyong.popularmovies.data.NetworkUtils;

import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String originalTitle = intent.getStringExtra(PopMoviesListActivity.ORIGINAL_TITLE);
        setTitle(originalTitle);

        String thumbnailUrl = intent.getStringExtra(PopMoviesListActivity.THUMBNAIL);
        ImageView thumbnail = findViewById(R.id.poster_thumbnail);
        String imageUrl = NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_185, thumbnailUrl);
        Glide.with(this).load(imageUrl).into(thumbnail);

        String releaseDate = intent.getStringExtra(PopMoviesListActivity.RELEASE_DATE);
        TextView tvReleaseData = findViewById(R.id.release_date);
        tvReleaseData.setText(releaseDate);

        double rating = intent.getDoubleExtra(PopMoviesListActivity.VOTE_AVERAGE, 0.0);
        TextView tvRating = findViewById(R.id.rating);
        tvRating.setText(String.format(Locale.ENGLISH, "%.1f / 10.0", rating));

        String overview =  intent.getStringExtra(PopMoviesListActivity.OVERVIEW);
        TextView tvOverview = findViewById(R.id.overview);
        tvOverview.setText(overview);
    }
}
