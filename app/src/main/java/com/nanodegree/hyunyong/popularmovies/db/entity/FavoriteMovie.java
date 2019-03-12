package com.nanodegree.hyunyong.popularmovies.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite_move")
public class FavoriteMovie {
    public FavoriteMovie(int id) {
        this.id = id;
    }

    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }
}
