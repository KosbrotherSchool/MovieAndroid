package com.jasonko.movietime.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kolichung on 8/25/15.
 */
public class MyYoutubeVideo implements Parcelable {

    String title;
    String youtube_id;
    int youtube_column_id;
    int youtube_sub_column_id;
    String subColumnName;

    public MyYoutubeVideo (String title, String youtube_id, int youtube_column_id, int youtube_sub_column_id, String subColumnName){
        this.title = title;
        this.youtube_id = youtube_id;
        this.youtube_column_id = youtube_column_id;
        this.youtube_sub_column_id = youtube_sub_column_id;
        this.subColumnName = subColumnName;
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

    public String getSubColumnName() {
        return subColumnName;
    }

    public String getPicLink(){
        return "https://i.ytimg.com/vi/"+ youtube_id+"/mqdefault.jpg";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
