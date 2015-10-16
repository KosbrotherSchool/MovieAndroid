package com.jasonko.movietime.api;

import android.util.Log;

import com.jasonko.movietime.model.BlogPost;

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
 * Created by kolichung on 10/14/15.
 */
public class BlogAPI {

    public static final String  TAG   = "BLOG_API";
    public static final boolean DEBUG = true;
    public static final String host = "http://139.162.10.76";

    public static ArrayList<BlogPost> getBlogPost(int page){
        ArrayList<BlogPost> posts = new ArrayList<>();
        String url = host + "/api/movie/blog_posts?page="+Integer.toString(page);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parsePosts(posts, message);
        }
        return posts;
    }

    private static void parsePosts(ArrayList<BlogPost> posts, String message) {

        try {
            JSONArray columnArray = new JSONArray(message);
            for (int i = 0; i < columnArray.length(); i++){
                JSONObject postObject = columnArray.getJSONObject(i);
                String title="";
                String link="";
                String pic_link="";
                String pub_date="";
                try {
                    title = postObject.getString("title");
                    link = postObject.getString("link");
                    pub_date = postObject.getString("pub_date");
                    pic_link = postObject.getString("pic_link");
                }catch (Exception e){

                }

                BlogPost newPost = new BlogPost(title,link,pic_link,pub_date);
                posts.add(newPost);
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
