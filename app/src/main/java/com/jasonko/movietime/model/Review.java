package com.jasonko.movietime.model;

/**
 * Created by kolichung on 10/1/15.
 */
public class Review {

    int review_id;
    int movie_id;
    String author;
    String title;
    String content;
    String publish_date;

    double point;
    int head_index;

    public Review(int review_id, int movie_id, String author, String title, String content, String publish_date, int head_index, double point) {
        this.review_id = review_id;
        this.movie_id = movie_id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.publish_date = publish_date;
        this.head_index = head_index;
        this.point = point;
    }

    public int getReview_id() {
        return review_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public double getPoint() {
        return point;
    }

    public int getHead_index() {
        return head_index;
    }

}
