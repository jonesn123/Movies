package com.nanodegree.hyunyong.popularmovies.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nanodegree.hyunyong.popularmovies.db.entity.FavoriteMovie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {
    @Query("SELECT * FROM favorite_move WHERE `id` = :id")
    FavoriteMovie getFavoriteMovie(int id);

    @Query("SELECT * FROM favorite_move")
    List<FavoriteMovie> getFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteMovie favoriteMovie);

    @Delete
    void delete(FavoriteMovie favoriteMovie);
}
