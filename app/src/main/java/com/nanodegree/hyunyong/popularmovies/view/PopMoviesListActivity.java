package com.nanodegree.hyunyong.popularmovies.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nanodegree.hyunyong.popularmovies.R;
import com.nanodegree.hyunyong.popularmovies.data.Movie;
import com.nanodegree.hyunyong.popularmovies.data.NetworkUtils;
import com.nanodegree.hyunyong.popularmovies.db.AppDataBase;
import com.nanodegree.hyunyong.popularmovies.db.dao.MovieDao;
import com.nanodegree.hyunyong.popularmovies.utilities.OpenMovieJsonUtils;
import com.nanodegree.hyunyong.popularmovies.viewModel.MovieModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PopMoviesListActivity extends AppCompatActivity implements PopMovieAdapter.MovieAdapterOnClickHandler {

    public static final String MOVIE_ID = "movie_id";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String THUMBNAIL = "thumbnail";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String RELEASE_DATE = "release_data";
    public static final String INTENT_FILTER_FAVORITE_CHANGE = "favorite_change";

    private List<Movie> mMovieList;
    private PopMovieAdapter mAdapter;
    private MovieModel mMovieModel;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mMovieModel.findFavoriteMovies(context);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_movies_list);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(INTENT_FILTER_FAVORITE_CHANGE));
        setTitle(R.string.movies);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = findViewById(R.id.rv_pop_movie_list);
        setupRecyclerView(rv);
        mMovieModel = ViewModelProviders.of(this).get(MovieModel.class);
        mMovieModel.favoriteMovieLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies != null) {
                    setTitle(R.string.favorite_movies);
                    mMovieList.clear();
                    mMovieList.addAll(movies);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        loadMovieData();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        MovieDao movieDao = AppDataBase.getDatabase(this).movieDao();
        if (id == R.id.menu_popular) {
            setTitle(R.string.popular_movies);
            mMovieList.clear();
            mMovieList.addAll(movieDao.getMoviesOrderByPopularity());
            mAdapter.notifyDataSetChanged();
        } else if (id == R.id.menu_rating) {
            setTitle(R.string.high_rating_movies);
            mMovieList.clear();
            mMovieList.addAll(movieDao.getMoviesOrderByRating());
            mAdapter.notifyDataSetChanged();
        } else if (id == R.id.menu_favority) {
            setTitle(getString(R.string.favorite_movies));
            mMovieModel.findFavoriteMovies(this);
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
                AppDataBase dataBase = AppDataBase.getDatabase(PopMoviesListActivity.this);
                dataBase.movieDao().insertAll(movieData);
                mMovieList.addAll(movieData);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }
}
