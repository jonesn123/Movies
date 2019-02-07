package com.nanodegree.hyunyong.popularmovies.data;

public class Movie {

    private int vote_count;
    private int id;
    private boolean video;
    private double vote_average;
    private String title;
    private double popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String backdrop_path;
    private boolean adult;
    private String overview;
    private String date;


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

    public String getOriginalTitle() {
        return original_title;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public int getVoteCount() {
        return vote_count;
    }

    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getBackdropPath() {
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
}
