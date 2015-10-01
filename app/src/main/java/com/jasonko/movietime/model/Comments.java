package com.jasonko.movietime.model;

/**
 * Created by larry on 2015/10/1.
 */
public class Comments {
    private String userId;
    private String user_comment;
    private int user_point;

    public Comments(String id, String comment, int point){
        this.userId = id;
        this.user_comment = comment;
        this.user_point = point;

    }

    public String getUserId(){ return userId;}

    public String getUser_comment(){return user_comment;}

    public int getUser_point(){return user_point;}
}
