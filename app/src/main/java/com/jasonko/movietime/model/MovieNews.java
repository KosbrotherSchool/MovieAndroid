package com.jasonko.movietime.model;

import java.util.Date;

/**
 * Created by kolichung on 8/20/15.
 */
public class MovieNews {

    String title;
    String info;
    String news_link;
    String publish_day;
    String pic_link;
    Date publish_date;
    int news_type;
    int news_id;

    public MovieNews (String title, String info, String news_link, String publish_day, String pic_link,
                      Date publish_date, int news_type, int news_id){
        this.title = title;
        this.info = info;
        this.news_link = news_link;
        this.publish_day = publish_day;
        this.pic_link = pic_link;
        this.publish_date = publish_date;
        this.news_type = news_type;
        this.news_id = news_id;
    }

    public String getTitle(){
        return title;
    }

    public String getInfo(){
        return info;
    }

    public String getNews_link(){
        return news_link;
    }

    public String getPublish_day(){
        return publish_day;
    }

    public Date getPublish_date(){
        return publish_date;
    }

    public int getNews_type(){
        return news_type;
    }

    public int getNews_id(){
        return news_id;
    }
}
