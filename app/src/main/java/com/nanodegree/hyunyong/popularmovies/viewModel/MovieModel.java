package com.nanodegree.hyunyong.popularmovies.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.nanodegree.hyunyong.popularmovies.data.Movie;
import com.nanodegree.hyunyong.popularmovies.db.AppDataBase;
import com.nanodegree.hyunyong.popularmovies.db.dao.MovieDao;
import com.nanodegree.hyunyong.popularmovies.db.entity.FavoriteMovie;

import java.util.ArrayList;
import java.util.List;

public class MovieModel extends ViewModel {
    public final MutableLiveData<List<Movie>> favoriteMovieLiveData = new MutableLiveData<>();

    public void findFavoriteMovies(Context context) {

        MovieDao movieDao = AppDataBase.getDatabase(context).movieDao();
        List<FavoriteMovie> favoriteMovies = AppDataBase.getDatabase(context).favoriteMovieDao().getFavoriteMovies();
        List<Movie> movies = new ArrayList<>();
        for (FavoriteMovie favoriteMovie: favoriteMovies) {
            Movie movie = movieDao.getMovie(favoriteMovie.getId());
            movies.add(movie);
        }
        favoriteMovieLiveData.setValue(movies);
    }

}
