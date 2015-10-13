package com.jasonko.movietime.model;

/**
 * Created by kolichung on 8/20/15.
 */
public class MovieTime implements Comparable<MovieTime>{

    String remark;
    String movie_title;
    String movie_time;
    int movie_id;
    int theater_id;
    String movie_photo;
    int area_id;

    int movie_minute;

    public MovieTime(String remark, String movie_title, String movie_time, int movie_id, int theater_id, String movie_photo, int area_id) {
        this.remark = remark;
        this.movie_title = movie_title;
        this.movie_time = movie_time;
        this.movie_id = movie_id;
        this.theater_id = theater_id;
        this.movie_photo = movie_photo;
        this.area_id = area_id;

//        if (movie_time.substring(movie_time.indexOf(":")+1, movie_time.indexOf(":")+2 ).equals("0")){
//            this.movie_minute = Integer.parseInt(movie_time.substring(movie_time.indexOf("：")+2, movie_time.length()));
//        }else {
            this.movie_minute = Integer.parseInt(movie_time.substring(movie_time.indexOf("：")+1, movie_time.length()));
//        }

    }

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

    public int getArea_id() {
        return area_id;
    }


    @Override
    public int compareTo(MovieTime another) {
        return this.movie_minute - another.movie_minute;
    }
}
