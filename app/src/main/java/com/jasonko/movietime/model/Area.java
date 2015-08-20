package com.jasonko.movietime.model;

/**
 * Created by kolichung on 8/20/15.
 */
public class Area {

    String name;
    int area_id;

    public Area(String name, int area_id){
        this.name = name;
        this.area_id = area_id;
    }

    public String getName(){
        return name;
    }

    public int getArea_id(){
        return area_id;
    }
}
