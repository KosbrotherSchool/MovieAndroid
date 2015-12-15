package com.jasonko.movietime.model;

/**
 * Created by kolichung on 10/17/15.
 */
public class Reply {

    int reply_id;
    int message_id;
    String author;
    String content;
    String pub_date;
    int head_index;
    int like_count;

    public Reply(int reply_id, int message_id, String author, String content, String pub_date, int head_index, int like_count) {
        this.reply_id = reply_id;
        this.message_id = message_id;
        this.author = author;
        this.content = content;
        this.pub_date = pub_date;
        this.head_index = head_index;
        this.like_count = like_count;
    }

    public int getReply_id() {
        return reply_id;
    }

    public int getMessage_id() {
        return message_id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getPub_date() {
        return pub_date;
    }

    public int getHead_index() {
        return head_index;
    }

    public int getLike_count() {
        return like_count;
    }

}
