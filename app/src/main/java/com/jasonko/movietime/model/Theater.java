package com.jasonko.movietime.model;

/**
 * Created by kolichung on 8/20/15.
 */
public class Theater {

    String name;
    String address;
    String phone;
    int area_id;
    int theater_id;

    public Theater(String name, String address, String phone, int area_id, int theater_id) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.area_id = area_id;
        this.theater_id = theater_id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getArea_id() {
        return area_id;
    }

    public int getTheater_id() {
        return theater_id;
    }

}
