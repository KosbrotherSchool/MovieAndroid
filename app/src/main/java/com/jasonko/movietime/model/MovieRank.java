package com.jasonko.movietime.model;

/**
 * Created by kolichung on 8/28/15.
 */
public class MovieRank {

    int rank_type;
    int movie_id;
    int current_rank;
    int last_week_rank;
    int publish_weeks;
    int the_week;
    String static_duration;
    int expect_people;
    int total_people;
    String satisfied_num;

    public MovieRank(int rank_type, int movie_id, int current_rank, int last_week_rank,
                     int publish_weeks, int the_week, String static_duration,
                     int expect_people, int total_people, String satisfied_num){
        this.rank_type = rank_type;
        this.movie_id = movie_id;
        this.current_rank = current_rank;
        this.last_week_rank = last_week_rank;
        this.publish_weeks = publish_weeks;
        this.the_week = the_week;
        this.static_duration = static_duration;
        this.expect_people = expect_people;
        this.total_people = total_people;
        this.satisfied_num = satisfied_num;
    }


    public int getRank_type() {
        return rank_type;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public int getCurrent_rank() {
        return current_rank;
    }

    public int getLast_week_rank() {
        return last_week_rank;
    }

    public int getPublish_weeks() {
        return publish_weeks;
    }

    public int getThe_week() {
        return the_week;
    }

    public String getStatic_duration() {
        return static_duration;
    }

    public int getExpect_people() {
        return expect_people;
    }

    public int getTotal_people() {
        return total_people;
    }

    public String getSatisfied_num() {
        return satisfied_num;
    }

}
