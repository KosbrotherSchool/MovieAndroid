package com.jasonko.movietime.model;

/**
 * Created by kolichung on 9/23/15.
 */
public class Blogger {

    String blogger_name;
    String blogger_url;
    int blogger_icon_id;

    public Blogger(String blogger_name, String blogger_url, int blogger_icon_id) {
        this.blogger_name = blogger_name;
        this.blogger_url = blogger_url;
        this.blogger_icon_id = blogger_icon_id;
    }

    public String getBlogger_name() {
        return blogger_name;
    }

    public String getBlogger_url() {
        return blogger_url;
    }

    public int getBlogger_icon_id() {
        return blogger_icon_id;
    }

}
