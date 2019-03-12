package com.nanodegree.hyunyong.popularmovies.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nanodegree.hyunyong.popularmovies.data.Movie;
import com.nanodegree.hyunyong.popularmovies.db.dao.FavoriteMovieDao;
import com.nanodegree.hyunyong.popularmovies.db.dao.MovieDao;
import com.nanodegree.hyunyong.popularmovies.db.entity.FavoriteMovie;

@Database(entities = {Movie.class, FavoriteMovie.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract FavoriteMovieDao favoriteMovieDao();

    private static final String DATABASE_NAME = "movie-db";
    private static AppDataBase INSTANCE = null;

    public static AppDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class, DATABASE_NAME).allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}
