package com.jasonko.movietime.model;

/**
 * Created by kolichung on 8/31/15.
 */
public class OrderTheater {

    String theater_name;
    String theater_url;
    int theater_icon_id;

    public OrderTheater(String theater_name, String theater_url, int theater_icon_id) {
        this.theater_name = theater_name;
        this.theater_url = theater_url;
        this.theater_icon_id = theater_icon_id;
    }

    public String getTheater_name() {
        return theater_name;
    }

    public String getTheater_url() {
        return theater_url;
    }

    public int getTheater_icon_id() {
        return theater_icon_id;
    }


}
