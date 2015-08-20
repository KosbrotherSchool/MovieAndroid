package com.jasonko.movietime.model;

/**
 * Created by kolichung on 8/20/15.
 */
public class Trailer {

    String title;
    String youtube_id;
    String youtube_link;
    int movie_id;
    int trailer_id;

    public Trailer(String title, String youtube_id, String youtube_link, int movie_id, int trailer_id) {
        this.title = title;
        this.youtube_id = youtube_id;
        this.youtube_link = youtube_link;
        this.movie_id = movie_id;
        this.trailer_id = trailer_id;
    }

    public String getTitle() {
        return title;
    }

    public String getYoutube_id() {
        return youtube_id;
    }

    public String getYoutube_link() {
        return youtube_link;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public int getTrailer_id() {
        return trailer_id;
    }

}
