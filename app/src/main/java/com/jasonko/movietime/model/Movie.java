package com.jasonko.movietime.model;

import java.util.Date;

/**
 * Created by kolichung on 8/20/15.
 */
public class Movie {

    String title;
    String title_eng;
    String movie_class;
    String movie_type;
    String movie_length;
    String publish_date;
    String director;
    String editors;
    String actors;
    String official;
    String movie_info;
    String small_pic;
    String large_pic;

    Date publish_date_date;
    int  movie_round;
    int movie_id;
    int photo_size;
    int trailer_size;

    double points;
    int review_size;

    MovieRank movieRank;

    public Movie(String title, String title_eng, String movie_class, String movie_type, String movie_length, String publish_date, String director, String editors, String actors, String official, String movie_info, String small_pic, String large_pic, Date publish_date_date, int movie_round, int movie_id, int photo_size, int trailer_size, double points, int review_size, MovieRank movieRank) {
        this.title = title;
        this.title_eng = title_eng;
        this.movie_class = movie_class;
        this.movie_type = movie_type;
        this.movie_length = movie_length;
        this.publish_date = publish_date;
        this.director = director;
        this.editors = editors;
        this.actors = actors;
        this.official = official;
        this.movie_info = movie_info;
        this.small_pic = small_pic;
        this.large_pic = large_pic;
        this.publish_date_date = publish_date_date;
        this.movie_round = movie_round;
        this.movie_id = movie_id;
        this.photo_size = photo_size;
        this.trailer_size = trailer_size;
        this.points = points;
        this.review_size = review_size;
        this.movieRank = movieRank;
    }

    public Movie(String title, String title_eng, String movie_class, String movie_type, String movie_length,
                 String publish_date, String director, String editors, String actors, String official, String movie_info,
                 String small_pic, String large_pic, Date publish_date_date, int movie_round, int movie_id, MovieRank movieRank,
                 int photo_size, int trailer_size){
        this.title = title;
        this.title_eng = title_eng;
        this.movie_class = movie_class;
        this.movie_type = movie_type;
        this.movie_length = movie_length;
        this.publish_date = publish_date;
        this.director = director;
        this.editors = editors;
        this.actors = actors;
        this.official = official;
        this.movie_info = movie_info;
        this.small_pic = small_pic;
        this.large_pic = large_pic;
        this.publish_date_date = publish_date_date;
        this.movie_round = movie_round;
        this.movie_id = movie_id;
        this.movieRank = movieRank;
        this.photo_size = photo_size;
        this.trailer_size = trailer_size;
    }

    public MovieRank getMovieRank() {
        return movieRank;
    }

    public String getTitle(){
        return  title;
    }

    public String getTitle_eng(){
        return  title_eng;
    }

    public String getMovie_class(){
        return movie_class;
    }

    public String getMovie_type(){
        return movie_type;
    }

    public String getMovie_length(){
        return movie_length;
    }

    public String getPublish_date(){
        return publish_date;
    }

    public String getDirector(){
        return director;
    }

    public String getEditors(){
        return editors;
    }

    public String getActors(){
        return actors;
    }

    public String getOfficial(){
        return official;
    }

    public String getMovie_info(){
        return movie_info;
    }

    public String getSmall_pic(){
        return small_pic;
    }

    public String getLarge_pic(){
        return large_pic;
    }

    public Date getPublish_date_date(){
        return publish_date_date;
    }

    public int getMovie_round(){
        return movie_round;
    }

    public int getMovie_id(){
        return movie_id;
    }

    public int getPhoto_size() {
        return photo_size;
    }

    public int getTrailer_size() {
        return trailer_size;
    }

    public double getPoints() {
        return points;
    }

    public int getReview_size() {
        return review_size;
    }
}
