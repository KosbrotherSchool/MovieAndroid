package com.jasonko.movietime.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table FOLLOW_MOVIE.
 */
public class FollowMovie {

    private Long id;
    /** Not-null value. */
    private String title;
    /** Not-null value. */
    private String movie_class;
    /** Not-null value. */
    private String small_pic;
    private int movie_id;
    /** Not-null value. */
    private java.util.Date publish_day;
    /** Not-null value. */
    private java.util.Date update_date;

    public FollowMovie() {
    }

    public FollowMovie(Long id) {
        this.id = id;
    }

    public FollowMovie(Long id, String title, String movie_class, String small_pic, int movie_id, java.util.Date publish_day, java.util.Date update_date) {
        this.id = id;
        this.title = title;
        this.movie_class = movie_class;
        this.small_pic = small_pic;
        this.movie_id = movie_id;
        this.publish_day = publish_day;
        this.update_date = update_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getTitle() {
        return title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Not-null value. */
    public String getMovie_class() {
        return movie_class;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMovie_class(String movie_class) {
        this.movie_class = movie_class;
    }

    /** Not-null value. */
    public String getSmall_pic() {
        return small_pic;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSmall_pic(String small_pic) {
        this.small_pic = small_pic;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    /** Not-null value. */
    public java.util.Date getPublish_day() {
        return publish_day;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPublish_day(java.util.Date publish_day) {
        this.publish_day = publish_day;
    }

    /** Not-null value. */
    public java.util.Date getUpdate_date() {
        return update_date;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUpdate_date(java.util.Date update_date) {
        this.update_date = update_date;
    }

}
