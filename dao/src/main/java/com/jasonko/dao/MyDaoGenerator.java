package com.jasonko.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by kolichung on 9/6/15.
 */
public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(3, "com.jasonko.movietime.dao");

        addFollowMovie(schema);
        addFavoriteTheater(schema);
        addFavoriteMessage(schema);

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

    private static void addFavoriteTheater(Schema schema){
        Entity favoriteTheater = schema.addEntity("FavoriteTheater");
        favoriteTheater.addIdProperty();
        favoriteTheater.addIntProperty("theater_id").notNull();
        favoriteTheater.addIntProperty("area_id").notNull();
        favoriteTheater.addStringProperty("name").notNull();
        favoriteTheater.addStringProperty("address").notNull();
        favoriteTheater.addStringProperty("phone").notNull();
    }

    private static void addFavoriteMessage (Schema schema){
        Entity favoriteTheater = schema.addEntity("FavoriteMessage");
        favoriteTheater.addIdProperty();
        favoriteTheater.addIntProperty("message_id").notNull();
        favoriteTheater.addStringProperty("author").notNull();
        favoriteTheater.addStringProperty("title").notNull();
        favoriteTheater.addStringProperty("tag").notNull();
        favoriteTheater.addStringProperty("content").notNull();
        favoriteTheater.addStringProperty("pub_date").notNull();
        favoriteTheater.addIntProperty("view_count").notNull();
        favoriteTheater.addIntProperty("like_count").notNull();
        favoriteTheater.addIntProperty("reply_size").notNull();
        favoriteTheater.addIntProperty("head_index").notNull();
        favoriteTheater.addBooleanProperty("is_head").notNull();
        favoriteTheater.addStringProperty("link_url").notNull();
    }

//    private static void addRecentMovie(Schema schema) {
//        Entity recentMovie = schema.addEntity("RecentMovie");
//        recentMovie.addIdProperty();
//        recentMovie.addStringProperty("title").notNull();
//        recentMovie.addStringProperty("movie_class").notNull();
//        recentMovie.addStringProperty("movie_type").notNull();
//        recentMovie.addStringProperty("actors").notNull();
//        recentMovie.addStringProperty("publish_date").notNull();
//        recentMovie.addStringProperty("small_pic").notNull();
//        recentMovie.addIntProperty("movie_id").notNull();
//        recentMovie.addDateProperty("update_date").notNull();
//    }


}
