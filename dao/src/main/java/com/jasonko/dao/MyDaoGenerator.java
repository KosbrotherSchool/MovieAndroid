package com.jasonko.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by kolichung on 9/6/15.
 */
public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.jasonko.movietime.dao");

        addRecentMovie(schema);
        addFollowMovie(schema);

        new DaoGenerator().generateAll(schema, "../Movietime/app/src/main/java");
    }

    private static void addFollowMovie(Schema schema) {
        Entity followMovie = schema.addEntity("FollowMovie");
        followMovie.addIdProperty();
        followMovie.addStringProperty("title").notNull();
        followMovie.addStringProperty("movie_class").notNull();
        followMovie.addStringProperty("small_pic").notNull();
        followMovie.addIntProperty("movie_id").notNull();
        followMovie.addDateProperty("publish_day").notNull();
        followMovie.addDateProperty("update_date").notNull();
    }

    private static void addRecentMovie(Schema schema) {
        Entity recentMovie = schema.addEntity("RecentMovie");
        recentMovie.addIdProperty();
        recentMovie.addStringProperty("title").notNull();
        recentMovie.addStringProperty("movie_class").notNull();
        recentMovie.addStringProperty("movie_type").notNull();
        recentMovie.addStringProperty("actors").notNull();
        recentMovie.addStringProperty("publish_date").notNull();
        recentMovie.addStringProperty("small_pic").notNull();
        recentMovie.addIntProperty("movie_id").notNull();
        recentMovie.addDateProperty("update_date").notNull();
    }


}
