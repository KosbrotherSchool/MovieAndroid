package com.jasonko.movietime.model;

/**
 * Created by kolichung on 8/25/15.
 */
public class MyYoutubeVideo {

    String title;
    String youtube_id;

    int youtube_column_id;
    int youtube_sub_column_id;

    public MyYoutubeVideo (String title, String youtube_id, int youtube_column_id, int youtube_sub_column_id){
        this.title = title;
        this.youtube_id = youtube_id;
        this.youtube_column_id = youtube_column_id;
        this.youtube_sub_column_id = youtube_sub_column_id;
    }

    public String getTitle(){
        return title;
    }

    public String getYoutubeID(){
        return youtube_id;
    }

    public int getYoutube_column_id(){
        return youtube_column_id;
    }

    public int getYoutube_sub_column_id(){
        return youtube_sub_column_id;
    }

}
