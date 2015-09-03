package com.jasonko.movietime.model;

/**
 * Created by kolichung on 9/1/15.
 */
public class DrawerItem {

    String title;
    int icon_id;

    public DrawerItem(String title, int icon_id) {
        this.title = title;
        this.icon_id = icon_id;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon_id() {
        return icon_id;
    }


}
