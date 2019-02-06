package com.nanodegree.hyunyong.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.hyunyong.popularmovies.data.NetworkUtils;
import com.nanodegree.hyunyong.popularmovies.utilities.OpenMovieJsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopMoviesListActivity extends AppCompatActivity implements PopMovieAdapter.MovieAdapterOnClickHandelr {

    private List<String> mMovieList;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_movies_list);
        setTitle(R.string.pop_movies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.rv_pop_movie_list);
        setupRecyclerView(mRecyclerView);
        loadMovieData();
    }

    private void loadMovieData() {
        new FetchMovieTask().execute();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mMovieList = new ArrayList<>();
        recyclerView.setAdapter(new PopMovieAdapter(mMovieList, this));
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick() {

    }

    public class FetchMovieTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {

            URL popularMovieRequestUrl = NetworkUtils.buildPopularMovieURL();
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(popularMovieRequestUrl);
                String[] simpleJsonMovieData = OpenMovieJsonUtils
                        .getImageURLStringsFromJson(PopMoviesListActivity.this, jsonMovieResponse);

                return simpleJsonMovieData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] movieData) {
            if (movieData != null) {
                mMovieList.addAll(Arrays.asList(movieData));
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }
}
