package com.jasonko.movietime.model;

/**
 * Created by kolichung on 8/28/15.
 */
public class MyYoutubeColumn {

    String name;
    int column_id;
    String image_link;

    public MyYoutubeColumn(String name, int column_id, String image_link) {
        this.name = name;
        this.column_id = column_id;
        this.image_link = image_link;
    }


    public String getName() {
        return name;
    }

    public int getColumn_id() {
        return column_id;
    }

    public String getImage_link() {
        return image_link;
    }


}
