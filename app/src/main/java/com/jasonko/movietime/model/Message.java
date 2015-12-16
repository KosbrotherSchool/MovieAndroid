package com.jasonko.movietime.model;

import java.io.Serializable;

/**
 * Created by kolichung on 10/14/15.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;

    int board_id;
    int message_id;
    String author;
    String title;
    String tag;
    String content;
    String pub_date;
    int view_count;
    int like_count;
    int reply_size;
    int head_index;
    boolean is_head;
    String link_url;
    String pic_url;

    public Message(int board_id, int message_id, String author, String title, String tag, String content, String pub_date, int view_count, int like_count, int reply_size, int head_index, boolean is_head, String link_url, String pic_url) {
        this.board_id = board_id;
        this.message_id = message_id;
        this.author = author;
        this.title = title;
        this.tag = tag;
        this.content = content;
        this.pub_date = pub_date;
        this.view_count = view_count;
        this.like_count = like_count;
        this.reply_size = reply_size;
        this.head_index = head_index;
        this.is_head = is_head;
        this.link_url = link_url;
        this.pic_url = pic_url;
    }


    public int getBoard_id() {
        return board_id;
    }

    public int getMessage_id() {
        return message_id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getTag() {
        return tag;
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

    public int getLike_count() {
        return like_count;
    }

    public int getReply_size() {
        return reply_size;
    }

    public int getHead_index() {
        return head_index;
    }

    public boolean is_head() {
        return is_head;
    }

    public String getLink_url() {
        return link_url;
    }

    public String getPic_url() {
        return pic_url;
    }

}
