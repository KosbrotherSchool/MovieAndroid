package com.jasonko.movietime.api;

import android.util.Log;

import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.model.MovieRank;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kolichung on 10/14/15.
 */
public class RankAPI {

    public static final String  TAG   = "RANK_API";
    public static final boolean DEBUG = true;
    public static final String host = "http://139.162.10.76";

    public static ArrayList<Movie> getMoiveReviewRank(int page){
        ArrayList<Movie> movies = new ArrayList<>();
        String url = host + "/api/movie/review_rank?page="+Integer.toString(page);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovie(movies, message);
        }
        return movies;
    }

    public static ArrayList<Movie> getMoivePointRank(int page){
        ArrayList<Movie> movies = new ArrayList<>();
        String url = host + "/api/movie/point_rank?page="+Integer.toString(page);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovie(movies, message);
        }
        return movies;
    }

    private static void parseMovie(ArrayList<Movie> movies, String message){
        try {
            JSONArray movieArray = new JSONArray(message);
            for (int i = 0; i < movieArray.length(); i++){

                JSONObject movieObject = movieArray.getJSONObject(i);

                String title = "";
                String title_eng = "";
                String movie_class = "";
                String movie_type = "";
                String movie_length = "";
                String publish_date = "";
                String director = "";
                String editors = "";
                String actors = "";
                String official = "";
                String movie_info = "";
                String small_pic = "";
                String large_pic = "";

                Date publish_date_date = null;
                int  movie_round = 0;
                int movie_id = 0;

                MovieRank movieRank;

                int photo_size = 0;
                int trailer_size = 0;

                try {
                    movie_length = movieObject.getString("movie_length");
                }catch (Exception e){

                }

                try {
                    title = movieObject.getString("title");
                }catch (Exception e){

                }

                try {
                    title_eng = movieObject.getString("title_eng");
                }catch (Exception e){

                }

                try {
                    movie_class = movieObject.getString("movie_class");
                }catch (Exception e){

                }

                try {
                    movie_type = movieObject.getString("movie_type");
                }catch (Exception e){

                }

                try {
                    publish_date = movieObject.getString("publish_date");
                }catch (Exception e){

                }

                try {
                    director = movieObject.getString("director");
                }catch (Exception e){

                }

                try {
                    editors = movieObject.getString("editors");
                }catch (Exception e){

                }

                try {
                    actors = movieObject.getString("actors");
                }catch (Exception e){

                }

                try {
                    official = movieObject.getString("official");
                }catch (Exception e){

                }

                try {
                    movie_info = movieObject.getString("movie_info");
                }catch (Exception e){

                }

                try {
                    small_pic = movieObject.getString("small_pic");
                }catch (Exception e){

                }

                try {
                    large_pic = movieObject.getString("large_pic");
                }catch (Exception e){

                }

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    publish_date_date = sdf.parse(movieObject.getString("publish_date_date"));
                }catch (Exception e){

                }

                try {
                    movie_round = movieObject.getInt("movie_round");
                }catch (Exception e){

                }


                try {
                    movie_id = movieObject.getInt("id");
                }catch (Exception e){

                }

                int rank_type = 0;
                int current_rank = 0;
                int last_week_rank = 0;
                int publish_weeks = 0;
                int the_week = 0;
                String static_duration = "";
                int expect_people = 0;
                int total_people = 0;
                String satisfied_num = "";

                try {
                    rank_type = movieObject.getInt("rank_type");
                }catch (Exception e){

                }

                try {
                    current_rank = movieObject.getInt("current_rank");
                }catch (Exception e){

                }

                try {
                    last_week_rank = movieObject.getInt("last_week_rank");
                }catch (Exception e){

                }

                try {
                    publish_weeks = movieObject.getInt("publish_weeks");
                }catch (Exception e){

                }

                try {
                    the_week = movieObject.getInt("the_week");
                }catch (Exception e){

                }

                try {
                    static_duration = movieObject.getString("static_duration");
                }catch (Exception e){

                }

                try {
                    expect_people = movieObject.getInt("expect_people");
                }catch (Exception e){

                }

                try {
                    total_people = movieObject.getInt("total_people");
                }catch (Exception e){

                }

                try {
                    satisfied_num = movieObject.getString("satisfied_num");
                }catch (Exception e){

                }

                try {
                    photo_size = movieObject.getInt("photo_size");
                    trailer_size = movieObject.getInt("trailer_size");
                }catch (Exception e){

                }

                double points =0;
                try {
                    points = movieObject.getDouble("point");
                }catch (Exception e){

                }

                int review_size = 0;
                try {
                    review_size = movieObject.getInt("review_size");
                }catch (Exception e){

                }

                movieRank = new MovieRank(rank_type,movie_id,current_rank,last_week_rank,publish_weeks,the_week,static_duration,expect_people,total_people,satisfied_num);

                Movie newMoive =new Movie(title,title_eng,movie_class,movie_type,movie_length,publish_date,director,editors,actors,official,movie_info,small_pic,large_pic,publish_date_date,movie_round,movie_id,photo_size,trailer_size,points,review_size, movieRank);
                movies.add(newMoive);
            }
        }catch (Exception e){

        }
    }


    public static String getMessageFromServer(String requestMethod, String apiPath, JSONObject json, String apiUrl) {
        URL url;
        try {

            url = new URL(apiUrl);

            if (DEBUG)
                Log.d(TAG, "URL: " + url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);

            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            if (requestMethod.equalsIgnoreCase("POST"))
                connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            if (requestMethod.equalsIgnoreCase("POST")) {
                OutputStream outputStream;

                outputStream = connection.getOutputStream();
                if (DEBUG)
                    Log.d("post message", json.toString());

                outputStream.write(json.toString().getBytes());
                outputStream.flush();
                outputStream.close();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder lines = new StringBuilder();

            String tempStr;

            while ((tempStr = reader.readLine()) != null) {
                lines = lines.append(tempStr);
            }
            if (DEBUG)
                Log.d("MOVIE_API", lines.toString());

            reader.close();
            connection.disconnect();

            return lines.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
