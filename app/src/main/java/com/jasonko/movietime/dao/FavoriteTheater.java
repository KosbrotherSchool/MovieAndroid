package com.jasonko.movietime.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table FAVORITE_THEATER.
 */
public class FavoriteTheater {

    private Long id;
    private int theater_id;
    private int area_id;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String address;
    /** Not-null value. */
    private String phone;

    public FavoriteTheater() {
    }

    public FavoriteTheater(Long id) {
        this.id = id;
    }

    public FavoriteTheater(Long id, int theater_id, int area_id, String name, String address, String phone) {
        this.id = id;
        this.theater_id = theater_id;
        this.area_id = area_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTheater_id() {
        return theater_id;
    }

    public void setTheater_id(int theater_id) {
        this.theater_id = theater_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getAddress() {
        return address;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Not-null value. */
    public String getPhone() {
        return phone;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPhone(String phone) {
        this.phone = phone;
    }

}