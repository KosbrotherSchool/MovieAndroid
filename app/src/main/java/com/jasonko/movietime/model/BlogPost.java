package com.jasonko.movietime.model;

/**
 * Created by kolichung on 10/14/15.
 */
public class BlogPost {

    public BlogPost(String title, String link, String pic_link, String pub_date) {
        this.title = title;
        this.link = link;
        this.pic_link = pic_link;
        this.pub_date = pub_date;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPic_link() {
        return pic_link;
    }

    public String getPub_date() {
        return pub_date;
    }

    String title;
    String link;
    String pic_link;
    String pub_date;



}
