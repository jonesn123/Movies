package com.nanodegree.hyunyong.popularmovies.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nanodegree.hyunyong.popularmovies.data.Movie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {
    @Query("SELECT * FROM favorite_movies")
    List<Movie> getAll();

    @Insert
    void insertAll(Movie movie);

    @Delete
    void delete(Movie movie);
}
