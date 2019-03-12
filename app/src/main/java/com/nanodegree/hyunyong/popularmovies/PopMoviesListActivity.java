package com.nanodegree.hyunyong.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nanodegree.hyunyong.popularmovies.data.Movie;
import com.nanodegree.hyunyong.popularmovies.data.NetworkUtils;
import com.nanodegree.hyunyong.popularmovies.utilities.OpenMovieJsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PopMoviesListActivity extends AppCompatActivity implements PopMovieAdapter.MovieAdapterOnClickHandler {

    public static final String MOVIE_ID = "movie_id";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String THUMBNAIL = "thumbnail";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String RELEASE_DATE = "release_data";

    private List<Movie> mMovieList;
    private PopMovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_movies_list);
        setTitle(R.string.pop_movies);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = findViewById(R.id.rv_pop_movie_list);
        setupRecyclerView(rv);
        loadMovieData();
    }

    private void loadMovieData() {
        new FetchMovieTask().execute();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mMovieList = new ArrayList<>();
        mAdapter = new PopMovieAdapter(mMovieList, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pop_movies_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_popular) {
            Collections.sort(mMovieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie movie1, Movie movie2) {
                    if (movie1.getPopularity() < movie2.getPopularity()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            mAdapter.notifyDataSetChanged();
            return true;
        } else if (id == R.id.menu_rating) {
            Collections.sort(mMovieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie movie1, Movie movie2) {
                    if (movie1.getVote_average() < movie2.getVote_average()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            mAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MOVIE_ID, movie.getId());
        intent.putExtra(ORIGINAL_TITLE, movie.getOriginal_title());
        intent.putExtra(THUMBNAIL, movie.getPoster_path());
        intent.putExtra(RELEASE_DATE, movie.getDate());
        intent.putExtra(OVERVIEW, movie.getOverview());
        intent.putExtra(VOTE_AVERAGE, movie.getVote_average());
        startActivity(intent);
    }

    private class FetchMovieTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... voids) {

            URL popularMovieRequestUrl = NetworkUtils.buildPopularMovieURL();
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(popularMovieRequestUrl);

                return OpenMovieJsonUtils
                        .getImageURLStringsFromJson(jsonMovieResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movieData) {
            if (movieData != null) {
                mMovieList.addAll(movieData);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
