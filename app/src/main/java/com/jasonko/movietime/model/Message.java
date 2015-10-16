package com.jasonko.movietime.model;

/**
 * Created by kolichung on 10/14/15.
 */
public class Message {

    public int getMessage_id() {
        return message_id;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage_tag() {
        return message_tag;
    }

    public String getContent() {
        return content;
    }

    public String getPub_date() {
        return pub_date;
    }

    public int getView_count() {
        return view_count;
    }

    public String getTitle() {
        return title;
    }

    public Message(int message_id, String author, String title, String message_tag, String content, String pub_date, int view_count) {
        this.message_id = message_id;
        this.author = author;
        this.title = title;
        this.message_tag = message_tag;
        this.content = content;
        this.pub_date = pub_date;
        this.view_count = view_count;
    }

    int message_id;
    String author;
    String title;
    String message_tag;
    String content;
    String pub_date;
    int view_count;



}
