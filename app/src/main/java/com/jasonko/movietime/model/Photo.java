package com.jasonko.movietime.model;

/**
 * Created by kolichung on 8/20/15.
 */
public class Photo {

    String photo_link;
    int movie_id;

    public Photo (String photo_link, int movie_id){
        this.photo_link = photo_link;
        this.movie_id = movie_id;
    }

    public String getPhoto_link(){
        return photo_link;
    }

    public int getMovie_id(){
        return getMovie_id();
    }

}
