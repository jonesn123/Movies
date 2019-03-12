package com.nanodegree.hyunyong.popularmovies.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite_movies")
public class Movie {

    @ColumnInfo(name = "vote_count")
    private int vote_count;
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "video")
    private boolean video;
    @ColumnInfo(name = "vote_average")
    private double vote_average;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "popularity")
    private double popularity;
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    @ColumnInfo(name = "original_language")
    private String original_language;
    @ColumnInfo(name = "original_title")
    private String original_title;
    @ColumnInfo(name = "backdrop_path")
    private String backdrop_path;
    @ColumnInfo(name = "adult")
    private boolean adult;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "date")
    private String date;

    public int getVote_count() {
        return vote_count;
    }

    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getDate() {
        return date;
    }

    public Movie(int vote_count, int id, boolean video, double vote_average,
                 String title, double popularity, String poster_path,
                 String original_language, String original_title, String backdrop_path, boolean adult,
                 String overview, String date) {
        this.vote_count = vote_count;
        this.id = id;
        this.video = video;
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.date = date;
    }
}
