package com.jasonko.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by kolichung on 9/6/15.
 */
public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(4, "com.jasonko.movietime.dao");

        addFollowMovie(schema);
        addFavoriteTheater(schema);
        addFavoriteMessage(schema);
        addFavoriteReply(schema);
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
        Entity favoriteMessage = schema.addEntity("FavoriteMessage");
        favoriteMessage.addIdProperty();
        favoriteMessage.addIntProperty("message_id").notNull();
        favoriteMessage.addStringProperty("author").notNull();
        favoriteMessage.addStringProperty("title").notNull();
        favoriteMessage.addStringProperty("tag").notNull();
        favoriteMessage.addStringProperty("content").notNull();
        favoriteMessage.addStringProperty("pub_date").notNull();
        favoriteMessage.addIntProperty("view_count").notNull();
        favoriteMessage.addIntProperty("like_count").notNull();
        favoriteMessage.addIntProperty("reply_size").notNull();
        favoriteMessage.addIntProperty("head_index").notNull();
        favoriteMessage.addBooleanProperty("is_head").notNull();
        favoriteMessage.addStringProperty("link_url").notNull();
    }

    private static void addFavoriteReply (Schema schema){
        Entity favoriteReply = schema.addEntity("FavoriteReply");
        favoriteReply.addIdProperty();
        favoriteReply.addIntProperty("reply_id").notNull();
        favoriteReply.addIntProperty("message_id").notNull();
        favoriteReply.addStringProperty("author").notNull();
        favoriteReply.addStringProperty("content").notNull();
        favoriteReply.addStringProperty("pub_date").notNull();
        favoriteReply.addIntProperty("head_index").notNull();
        favoriteReply.addIntProperty("like_count").notNull();
    }




}
