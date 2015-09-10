package com.jasonko.movietime.model;

/**
 * Created by kolichung on 8/20/15.
 */
public class MovieTime {

    String remark;
    String movie_title;
    String movie_time;
    int movie_id;
    int theater_id;
    String movie_photo;

    public MovieTime(String remark, String movie_title, String movie_time, int movie_id, int theater_id, String movie_photo){
        this.remark = remark;
        this.movie_title = movie_title;
        this.movie_time = movie_time;
        this.movie_id = movie_id;
        this.theater_id = theater_id;
        this.movie_photo = movie_photo;
    }

    public String getRemark() {
        return remark;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public String getMovie_time() {
        return movie_time;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public int getTheater_id() {
        return theater_id;
    }

    public String getMovie_photo(){
        return movie_photo;
    }
}
