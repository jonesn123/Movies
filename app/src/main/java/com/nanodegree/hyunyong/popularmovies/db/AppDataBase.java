package com.nanodegree.hyunyong.popularmovies.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nanodegree.hyunyong.popularmovies.data.Movie;
import com.nanodegree.hyunyong.popularmovies.db.dao.FavoriteMovieDao;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract FavoriteMovieDao movieDao();
}
