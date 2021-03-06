package com.jasonko.movietime.api;

import android.util.Log;

import com.jasonko.movietime.model.Review;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
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
import java.util.List;

/**
 * Created by kolichung on 10/1/15.
 */
public class ReviewAPI {

    public static final String  TAG   = "REVIEW_API";
    public static final boolean DEBUG = true;
    public static final String host = "http://139.162.10.76";

    public static ArrayList<Review> getReviewsByMovieID(int movie_id,int page){
        ArrayList<Review> reviews = new ArrayList<>();
        String url = host + "/api/movie/reviews?movie_id="+ Integer.toString(movie_id)+"&page="+Integer.toString(page);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseReviews(reviews, message);
        }
        return reviews;
    }

    private static void parseReviews(ArrayList<Review> reviews, String message) {

        try {
            JSONArray columnArray = new JSONArray(message);
            for (int i = 0; i < columnArray.length(); i++){
                JSONObject reviewObject = columnArray.getJSONObject(i);
                int review_id = 0;
                int movie_id = 0;
                String author = "";
                String title = "";
                String content = "";
                String publish_date = "";
                double point = 0;
                int head_index = 0;
                try {
                    review_id = reviewObject.getInt("id");
                    movie_id = reviewObject.getInt("movie_id");
                    author = reviewObject.getString("author");
                    title = reviewObject.getString("title");
                    content = reviewObject.getString("content");
                    publish_date = reviewObject.getString("publish_date");
                    head_index = reviewObject.getInt("head_index");
                }catch (Exception e){

                }
                try {
                    point = reviewObject.getDouble("point");
                }catch (Exception e){

                }
                Review newReview = new Review(review_id,movie_id,author,title,content,publish_date,head_index,point);
                reviews.add(newReview);
            }
        }catch (Exception e){

        }

    }


    public static String httpPostReview(int movie_id, String author, String title, String content, String point, int head_index){
        String result = "" ;
        // 第一步，创建HttpPost对象
        HttpPost httpPost = new HttpPost( host + "/api/movie/update_reviews" );

        // 设置HTTP POST请求参数必须用NameValuePair对象
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("m", Integer.toString(movie_id)));
        params.add(new BasicNameValuePair("a", author));
        params.add(new BasicNameValuePair("t", title));
        params.add(new BasicNameValuePair("c", content));
        params.add(new BasicNameValuePair("p", point));
        params.add(new BasicNameValuePair("h", Integer.toString(head_index)));

        HttpResponse httpResponse = null;
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity( params , HTTP.UTF_8 ));
            HttpClient httpClient = new DefaultHttpClient() ;
            // 请求超时  10s
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000 ) ;
            // 读取超时  10s
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);

            httpResponse = httpClient.execute( httpPost ) ;
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                result  = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result ;
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
