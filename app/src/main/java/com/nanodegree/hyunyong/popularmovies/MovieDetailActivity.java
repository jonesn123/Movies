package com.nanodegree.hyunyong.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nanodegree.hyunyong.popularmovies.data.NetworkUtils;
import com.nanodegree.hyunyong.popularmovies.data.Video;
import com.nanodegree.hyunyong.popularmovies.utilities.OpenMovieJsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    private ListView mMovieList;
    private VideoListAdapter mMovieAdapter;
    private ArrayList<Video> mVedioList = new ArrayList<>();
    private int mMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        mMovieId = intent.getIntExtra(PopMoviesListActivity.MOVIE_ID, 0);
        String originalTitle = intent.getStringExtra(PopMoviesListActivity.ORIGINAL_TITLE);
        setTitle(originalTitle);

        mMovieList = findViewById(R.id.detail_list);
        setHeaderView(intent);

        mMovieAdapter = new VideoListAdapter(this, mVedioList);
        mMovieList.setAdapter(mMovieAdapter);

        loadVedioData();
    }

    private void loadVedioData() {
        new FetchVideoTask().execute();
    }

    private void setHeaderView(Intent intent) {
        View view = LayoutInflater.from(this).inflate(R.layout.detail_content, mMovieList, false);

        String thumbnailUrl = intent.getStringExtra(PopMoviesListActivity.THUMBNAIL);
        ImageView thumbnail = view.findViewById(R.id.poster_thumbnail);
        String imageUrl = NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_185, thumbnailUrl);
        Glide.with(this).load(imageUrl).into(thumbnail);

        String releaseDate = intent.getStringExtra(PopMoviesListActivity.RELEASE_DATE);
        TextView tvReleaseData = view.findViewById(R.id.release_date);
        tvReleaseData.setText(releaseDate);

        double rating = intent.getDoubleExtra(PopMoviesListActivity.VOTE_AVERAGE, 0.0);
        TextView tvRating = view.findViewById(R.id.rating);
        tvRating.setText(String.format(Locale.ENGLISH, "%.1f / 10.0", rating));

        String overview =  intent.getStringExtra(PopMoviesListActivity.OVERVIEW);
        TextView tvOverview = view.findViewById(R.id.overview);
        tvOverview.setText(overview);

        mMovieList.addHeaderView(view);
    }

    private class VideoListAdapter extends ArrayAdapter<Video> {

        private LayoutInflater inflater;
        public VideoListAdapter(Context context, ArrayList<Video> array) {
            super(context,0, array);
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            View view = inflater.inflate(R.layout.layout_movie_list, parent, false);
            final Video item = getItem(position);
            if (item == null) return view;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, item.getYouTubeUri()));
                }
            });

            TextView title = view.findViewById(R.id.title);
            title.setText(item.getName());
            return view;
        }
    }

    private class FetchVideoTask extends AsyncTask<Void, Void, List<Video>> {

        @Override
        protected List<Video> doInBackground(Void... movieId) {

            URL videoRequestURL = NetworkUtils.buildViDeoMovieURL(mMovieId);
            try {
                String jsonVideoResponse = NetworkUtils.getResponseFromHttpUrl(videoRequestURL);

                return OpenMovieJsonUtils
                        .getVideoURLStringsFromJson(jsonVideoResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Video> videos) {
            if (videos != null) {
                mMovieAdapter.clear();
                mMovieAdapter.addAll(videos);
                mMovieAdapter.notifyDataSetChanged();
            }
        }
    }
}
