package com.jasonko.movietime.api;

import android.util.Log;

import com.jasonko.movietime.model.MovieTime;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kolichung on 10/8/15.
 */
public class MovieTimeAPI {

    public static final String  TAG   = "MOVIE_TIME_API";
    public static final boolean DEBUG = true;
    public static final String host = "http://139.162.10.76";

    public static ArrayList<MovieTime> getMovieTimeByTime(String query_hour,int area_id, int theater_id){
        ArrayList<MovieTime> movieTimes = new ArrayList<>();
        String url;
        if (theater_id!=0) {
            url = host + "/api/movie/movie_by_time?time=" + query_hour+":" + "&area_id=" + Integer.toString(area_id) + "&theater_id=" + Integer.toString(theater_id);
        }else {
            url = host + "/api/movie/movie_by_time?time=" + query_hour+":" + "&area_id=" + Integer.toString(area_id);
        }
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovieTimes(movieTimes, message, query_hour);
        }
        return movieTimes;
    }

    private static void parseMovieTimes(ArrayList<MovieTime> movieTimes, String message, String query_hour) {

        try {
            JSONArray timesArray = new JSONArray(message);
            for (int i = 0; i < timesArray.length(); i++){
                JSONObject timesObject = timesArray.getJSONObject(i);

                String remark = "";
                String movie_title = "";
                String movie_time = "";
                int movie_id = 0;
                int theater_id = 0;
                String movie_photo="";
                int area_id = 0;

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

                try {
                    movie_photo = timesObject.getString("movie_photo");
                }catch (Exception e){

                }

                try {
                    area_id = timesObject.getInt("area_id");
                }catch (Exception e){

                }


                String theTime = movie_time.substring(movie_time.indexOf(query_hour+"："), movie_time.indexOf(query_hour+"：")+5);

                MovieTime newTime = new MovieTime(remark, movie_title, theTime, movie_id, theater_id, movie_photo, area_id);
                movieTimes.add(newTime);
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
