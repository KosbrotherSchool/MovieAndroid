package com.jasonko.movietime.api;

import android.util.Log;

import com.jasonko.movietime.model.Area;
import com.jasonko.movietime.model.Blogger;
import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.model.MovieNews;
import com.jasonko.movietime.model.MovieRank;
import com.jasonko.movietime.model.MovieTime;
import com.jasonko.movietime.model.Photo;
import com.jasonko.movietime.model.Trailer;
import com.jasonko.movietime.model.Version;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kolichung on 8/20/15.
 */
public class MovieAPI {

    public static final String  TAG   = "MOVIE_API";
    public static final boolean DEBUG = true;
    public static final String host = "http://139.162.10.76";

    public static Version getVersion() {
        Version theversion = null;
        String url = host + "/api/movie/version";
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            try {
                JSONObject theObject = new JSONObject(message);
                String versionName = theObject.getString("version_name");
                String versionContent = theObject.getString("version_content");
                int versionCode = theObject.getInt("version_code");
                theversion = new Version(versionName,versionContent,versionCode);
            }catch (Exception e){

            }

        }
        return theversion;
    }

    public static ArrayList<Blogger> getBlogs(){
        ArrayList<Blogger> bloggers = new ArrayList<>();
        String url = host + "/api/movie/blogs";
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseBloggers(bloggers, message);
        }
        return bloggers;
    }

    public static ArrayList<Trailer> getMovieTrailersByID(int movie_id){
        ArrayList<Trailer> trailers = new ArrayList<>();
        String url = host + "/api/movie/trailers?movie_id="+ Integer.toString(movie_id);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovieTrailers(trailers, message);
        }
        return trailers;
    }



    public static ArrayList<Movie> getMoviesByRoundID(int movie_round, int page){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String url;
        if (movie_round != 1) {
            url = host + "/api/movie/movies?movie_round=" + Integer.toString(movie_round) + "&page=" + Integer.toString(page);
        }else {
            url = host + "/api2/movie/pub_movies?page="+ Integer.toString(page);
        }
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovie(movies, message);
        }
        return movies;
    }

    public static ArrayList<Movie> getTaipeiRankMovies(int page){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String url = host + "/api/movie/rank_movies?rank_type=1&new_api=true";
        if (page != -1){
            url = url + "&page=" + Integer.toString(page);
        }
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovie(movies, message);
        }
        return movies;
    }


    public static ArrayList<Movie> getSearchedMovies(String query, int page){
        ArrayList<Movie> movies = new ArrayList<>();
        String url = host + "/api/movie/search?query=" + URLEncoder.encode(query) + "&page=" + Integer.toString(page);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovie(movies, message);
        }
        return movies;
    }

    public static ArrayList<Area> getMovieAreasByMovieID(int movie_id){
        ArrayList<Area> areas = new ArrayList<>();
        String url = host + "/api/movie/areas?movie_id="+Integer.toString(movie_id);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseAreas(areas, message);
        }
        return areas;
    }

    private static void parseAreas(ArrayList<Area> areas, String message) {
        try {
            JSONArray areaArray = new JSONArray(message);
            for (int i = 0; i < areaArray.length(); i++){
                JSONObject areaObject = areaArray.getJSONObject(i);

                String name = "";
                int area_id = 0;

                try {
                    name = areaObject.getString("name");
                    area_id = areaObject.getInt("id");
                }catch (Exception e){

                }
                Area newArea = new Area(name, area_id);
                areas.add(newArea);
            }
        }catch (Exception e){

        }
    }


    private static void parseBloggers(ArrayList<Blogger> bloggers, String message) {
        try {
            JSONArray blogArray = new JSONArray(message);
            for (int i = 0; i < blogArray.length(); i++){
                JSONObject blogObject = blogArray.getJSONObject(i);

                String blogger_name = "";
                String blogger_url = "";
                String pic_link = "";

                try {
                    blogger_name = blogObject.getString("title");
                    blogger_url = blogObject.getString("link");
                    pic_link = blogObject.getString("pic_link");
                }catch (Exception e){

                }
                Blogger newBlogger = new Blogger(blogger_name,blogger_url,pic_link);
                bloggers.add(newBlogger);
            }
        }catch (Exception e){

        }
    }


    public static ArrayList<Movie> getExpectRankMovies(){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String url = host + "/api/movie/rank_movies?rank_type=5";
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovie(movies, message);
        }
        return movies;
    }

    public static ArrayList<Movie> getSatisfyRankMovies(){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String url = host + "/api/movie/rank_movies?rank_type=6";
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

    public static Movie getSingleMovie(int movie_id){
        Movie theMovie = null;
        String url = host + "/api/movie/movies?movie_id="+ Integer.toString(movie_id);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            theMovie = parseSingleMovie(theMovie, message);
        }
        return theMovie;
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

    public static ArrayList<MovieTime> getTheaterMovieTimes(int theater_id){
        ArrayList<MovieTime> times = new ArrayList<MovieTime>();
        String url = host;
        if (theater_id != 0){
            url = url + "/api/movie/movietimes?theater=" + Integer.toString(theater_id);
        }

        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovieTimes(times, message);
        }
        return times;
    }

    public static ArrayList<MovieTime> getAreaMovieTimes(int movie_id, int area_id) {
        ArrayList<MovieTime> times = new ArrayList<MovieTime>();
        String url = host;

        url = url + "/api/movie/movietimes?movie=" + Integer.toString(movie_id) + "&area=" + Integer.toString(area_id);

        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMovieTimes(times, message);
        }
        return times;
    }



    private static void parseMovieTrailers(ArrayList<Trailer> trailers, String message) {
        try {
            JSONArray videoArray = new JSONArray(message);
            for (int i = 0; i < videoArray.length(); i++){
                JSONObject videoObject = videoArray.getJSONObject(i);
                String title = "";
                String youtube_id = "";
                String youtube_link = "";
                int movie_id = 0;
                int trailer_id = 0;
                try {
                    title = videoObject.getString("title");
                    youtube_id = videoObject.getString("youtube_id");
                    youtube_link = videoObject.getString("youtube_link");
                    movie_id = videoObject.getInt("movie_id");
                    trailer_id = videoObject.getInt("id");
                }catch (Exception e){

                }
                Trailer newTrailer = new Trailer(title, youtube_id,youtube_link,movie_id,trailer_id);
                trailers.add(newTrailer);
            }
        }catch (Exception e){

        }
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
                String movie_photo="";

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

                MovieTime newTime = new MovieTime(remark, movie_title, movie_time, movie_id, theater_id, movie_photo);
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
                    news_link = newsObject.getString("news_link");
                    publish_day = newsObject.getString("publish_day");
                    pic_link = newsObject.getString("pic_link");
                }catch (Exception e){

                }

                try{
                    info = newsObject.getString("info");
                }catch (Exception e){

                }

                try {
                    news_type = newsObject.getInt("news_type");
                }catch (Exception e){

                }

                try {
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

    private static Movie parseSingleMovie(Movie mMovie, String message) {
        try {
            JSONObject movieObject = new JSONObject(message);

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

            int photo_size = 0;
            int trailer_size = 0;

            int review_size = 0;
            double points = 0;

            try {
                title = movieObject.getString("title");
                title_eng = movieObject.getString("title_eng");
                movie_class = movieObject.getString("movie_class");
                movie_type = movieObject.getString("movie_type");
                movie_length = movieObject.getString("movie_length");
                publish_date = movieObject.getString("publish_date");
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
                small_pic = movieObject.getString("small_pic");
                large_pic = movieObject.getString("large_pic");
            }catch (Exception e){

            }

            try {
                movie_info = movieObject.getString("movie_info");
            }catch (Exception e){

            }

            try {
                movie_round = movieObject.getInt("movie_round");
                movie_id = movieObject.getInt("id");
            }catch (Exception e){

            }

            try {
                photo_size = movieObject.getInt("photo_size");
                trailer_size = movieObject.getInt("trailer_size");
            }catch (Exception e){

            }

            try {
                review_size = movieObject.getInt("review_size");
            }catch (Exception e){

            }

            try {
                points = movieObject.getDouble("point");
            }catch (Exception e){

            }

            String imdb_point = "0.0";
            String imdb_link = "";
            String potato_point = "0.0";
            String potato_link = "";

            try {
                if (!movieObject.getString("imdb_point").equals("null")) {
                    imdb_point = movieObject.getString("imdb_point");
                }
                if (!movieObject.getString("imdb_link").equals("null")) {
                    imdb_link = movieObject.getString("imdb_link");
                }
                if (!movieObject.getString("potato_point").equals("null")){
                    potato_point = movieObject.getString("potato_point");
                }

                if (!movieObject.getString("potato_link").equals("null")) {
                    potato_link = movieObject.getString("potato_link");
                }
            }catch (Exception e){

            }

            mMovie = new Movie(title,title_eng,movie_class,movie_type,movie_length,publish_date,director,editors,actors,official,movie_info,small_pic,large_pic,publish_date_date,movie_round,movie_id,photo_size,trailer_size,points,review_size,null,imdb_point,imdb_link, potato_point,potato_link);
        }catch (Exception e){

        }
        return mMovie;
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
