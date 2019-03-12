package com.nanodegree.hyunyong.popularmovies.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nanodegree.hyunyong.popularmovies.data.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY popularity")
    List<Movie> getMoviesOrderByPopularity();

    @Query("SELECT * FROM movies ORDER BY vote_average")
    List<Movie> getMoviesOrderByRating();

    @Query("SELECT * FROM movies WHERE `id` = :id")
    Movie getMovie(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movie> movie);

    @Delete
    void delete(Movie movie);
}
