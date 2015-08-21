package com.jasonko.movietime.api;

import android.util.Log;

import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.model.MovieNews;
import com.jasonko.movietime.model.MovieTime;
import com.jasonko.movietime.model.Photo;

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
 * Created by kolichung on 8/20/15.
 */
public class MovieAPI {

    public static final String  TAG   = "MOVIE_API";
    public static final boolean DEBUG = true;
    public static final String host = "http://106.185.27.30";

    public static ArrayList<Movie> getTaipeiRankMovies(){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String url = host + "/api/movie/rank_movies?rank_type=1";
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovie(movies, message);
        }
        return movies;
    }

    public static ArrayList<Movie> getUSRankMovies(){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String url = host + "/api/movie/rank_movies?rank_type=2";
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovie(movies, message);
        }
        return movies;
    }

    public static ArrayList<Movie> getWeekRankMovies(){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String url = host + "/api/movie/rank_movies?rank_type=3";
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovie(movies, message);
        }
        return movies;
    }

    public static ArrayList<Movie> getYearRankMovies(){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String url = host + "/api/movie/rank_movies?rank_type=4";
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovie(movies, message);
        }
        return movies;
    }

    public static ArrayList<MovieNews> getMovieNews(int news_type, int page){
        ArrayList<MovieNews> newses = new ArrayList<MovieNews>();
        String url = host + "/api/movie/news?news_type="+Integer.toString(news_type) + "&page=" +Integer.toString(page);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseNewses(newses, message);
        }
        return newses;
    }

    public static ArrayList<Photo> getMoviePhotosByID(int movie_id){
        ArrayList<Photo> photos = new ArrayList<Photo>();
        String url = host + "/api/movie/photos?movie_id=" + Integer.toString(movie_id);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parsePhotos(photos, message);
        }
        return photos;
    }

    public static ArrayList<MovieTime> getMovieTimes(int movie_id, int theater_id){
        ArrayList<MovieTime> times = new ArrayList<MovieTime>();
        String url = host;
        if ( movie_id != 0 && theater_id != 0) {
            url = url + "/api/movie/movietimes?movie_id=" + Integer.toString(movie_id) + "&theater_id=" + Integer.toString(theater_id);
        }else if (movie_id != 0){
            url = url + "/api/movie/movietimes?movie_id=" + Integer.toString(movie_id);
        }else if (theater_id != 0){
            url = url + "/api/movie/movietimes?theater_id=" + Integer.toString(theater_id);
        }

        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovieTimes(times, message);
        }
        return times;
    }

    private static void parseMovieTimes(ArrayList<MovieTime> times, String message) {
        try {
            JSONArray timesArray = new JSONArray(message);
            for (int i = 0; i < timesArray.length(); i++){
                JSONObject timesObject = timesArray.getJSONObject(i);

                String remark = "";
                String movie_title = "";
                String movie_time = "";
                int movie_id = 0;
                int theater_id = 0;

                try {
                    remark = timesObject.getString("remark");
                }catch (Exception e){

                }

                try {
                    movie_title = timesObject.getString("movie_title");
                }catch (Exception e){

                }

                try {
                    movie_time = timesObject.getString("movie_time");
                }catch (Exception e){

                }

                try {
                    movie_id = timesObject.getInt("movie_id");
                }catch (Exception e){

                }

                try {
                    theater_id = timesObject.getInt("theater_id");
                }catch (Exception e){

                }

                MovieTime newTime = new MovieTime(remark, movie_title, movie_time, movie_id, theater_id);
                times.add(newTime);
            }
        }catch (Exception e){

        }
    }

    private static void parseNewses(ArrayList<MovieNews> newses, String message) {
        try {
            JSONArray newsArray = new JSONArray(message);
            for (int i = 0; i < newsArray.length(); i++){
                JSONObject newsObject = newsArray.getJSONObject(i);

                String title = "";
                String info = "";
                String news_link = "";
                String publish_day = "";
                String pic_link = "";
                Date publish_date = null;
                int news_type = 0;
                int news_id = 0;
                try {
                    title = newsObject.getString("title");
                    info = newsObject.getString("info");
                    news_link = newsObject.getString("news_link");
                    publish_day = newsObject.getString("publish_day");
                    pic_link = newsObject.getString("pic_link");
                    news_type = newsObject.getInt("news_type");
                    news_id = newsObject.getInt("news_id");
                }catch (Exception e){

                }

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    publish_date = sdf.parse(newsObject.getString("publish_date"));
                }catch (Exception e){

                }

                MovieNews newNews = new MovieNews(title,info,news_link,publish_day,pic_link,publish_date,news_type,news_id);
                newses.add(newNews);
            }
        }catch (Exception e){

        }
    }

    private static void parsePhotos(ArrayList<Photo> photos, String message) {
        try {
            JSONArray photoArray = new JSONArray(message);
            for (int i = 0; i < photoArray.length(); i++){
                JSONObject photoObject = photoArray.getJSONObject(i);
                String link = "";
                int movie_id = 0;
                try {
                    link = photoObject.getString("photo_link");
                    movie_id = photoObject.getInt("movie_id");
                }catch (Exception e){

                }
                Photo newPhoto = new Photo(link, movie_id);
                photos.add(newPhoto);
            }
        }catch (Exception e){

        }
    }

    public static void parseMovie(ArrayList<Movie> movies, String message){
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
                    movie_type = movieObject.getString("moive_type");
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
                    movie_id = movieObject.getInt("movie_id");
                }catch (Exception e){

                }

                Movie newMoive = new Movie(title,title_eng,movie_class,movie_type, movie_length, publish_date, director, editors, actors, official, movie_info, small_pic, large_pic, publish_date_date, movie_round, movie_id);
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
            ;
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
