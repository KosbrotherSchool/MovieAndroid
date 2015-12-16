package com.jasonko.movietime.api;

import android.util.Log;

import com.jasonko.movietime.model.Message;
import com.jasonko.movietime.model.Reply;

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
 * Created by kolichung on 10/14/15.
 */
public class MessageAPI {

    public static final String  TAG   = "MESSAGE_API";
    public static final boolean DEBUG = true;
    public static final String host = "http://139.162.10.76";

    public static ArrayList<Message> getHighLightMessages(){
        ArrayList<Message> messages= new ArrayList<>();
        String url = host + "/api2/movie/highlight_messages";
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMessages(messages, message);
        }
        return messages;
    }

    public static ArrayList<Message> getMessages(int board_id, int page){
        ArrayList<Message> messages= new ArrayList<>();
        String url = host + "/api2/movie/message?board_id="+Integer.toString(board_id)+"&page="+Integer.toString(page);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseMessages(messages, message);
        }
        return messages;
    }

    public static String getMessageLikePlusOne(int message_id){
        String url = host + "/api2/movie/update_message_like?message_id="+Integer.toString(message_id);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            return message;
        }
    }

    public static String getReplyLikePlusOne(int reply_id){
        String url = host + "/api2/movie/update_reply_like?reply_id="+Integer.toString(reply_id);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            return message;
        }
    }

    public static ArrayList<Reply> getReplies(int message_id, int page){
        ArrayList<Reply> replies = new ArrayList<>();
        String url = host + "/api2/movie/reply?message_id="+Integer.toString(message_id)+"&page="+Integer.toString(page);
        String message = getMessageFromServer("GET", null, null, url);
        if (message == null) {
            return null;
        } else {
            parseReplies(replies, message);
        }
        return replies;
    }

    private static void parseReplies(ArrayList<Reply> replies, String message) {
        try {
            JSONArray columnArray = new JSONArray(message);
            for (int i = 0; i < columnArray.length(); i++){
                JSONObject messageObject = columnArray.getJSONObject(i);

                int reply_id = 0;
                int message_id = 0;
                String author = "";
                String content = "";
                String pub_date = "";
                int head_index = 1;
                int like_count = 0;

                try {
                    reply_id = messageObject.getInt("id");
                    message_id = messageObject.getInt("ios_message_id");
                    author = messageObject.getString("author");
                    content = messageObject.getString("content");
                    pub_date = messageObject.getString("pub_date");
                    head_index = messageObject.getInt("head_index");
                    like_count = messageObject.getInt("like_count");
                }catch (Exception e){

                }

                Reply newReply = new Reply(reply_id,message_id,author,content,pub_date,head_index,like_count);
                replies.add(newReply);
            }
        }catch (Exception e){

        }
    }

    private static void parseMessages(ArrayList<Message> messages, String message) {

        try {
            JSONArray columnArray = new JSONArray(message);
            for (int i = 0; i < columnArray.length(); i++){
                JSONObject messageObject = columnArray.getJSONObject(i);

                int board_id = -1;
                int message_id = 0;
                String author = "";
                String title = "";
                String tag = "";
                String content = "";
                String pub_date = "";
                int view_count = 0;
                int like_count = 0;
                int reply_size = 0;
                int head_index = 0;
                boolean is_head = false;
                String link_url = "";
                String pic_url = "";


                try {
                    message_id = messageObject.getInt("id");
                    board_id = messageObject.getInt("board_id");
                    title = messageObject.getString("title");
                    link_url = messageObject.getString("link_url");
                    pic_url = messageObject.getString("pic_link");
                }catch (Exception e){

                }

                try {
                    author = messageObject.getString("author");
                    pub_date = messageObject.getString("pub_date");
                    content = messageObject.getString("content");
                    tag = messageObject.getString("tag");
                    reply_size = messageObject.getInt("reply_size");
                    like_count = messageObject.getInt("like_count");
                    head_index = messageObject.getInt("head_index");
                }catch (Exception e){

                }

                Message newMessage = new Message(board_id,message_id,author,title,tag,content,pub_date,view_count,like_count,reply_size,head_index,is_head,link_url,pic_url);
                messages.add(newMessage);
            }
        }catch (Exception e){

        }

    }


    public static String httpPostMessage(int board_id,String author, String title, String tag, String content,int head_index, String link_url){
        String result = "" ;
        // 第一步，创建HttpPost对象
        HttpPost httpPost = new HttpPost( host + "/api2/movie/update_messages" );

        // 设置HTTP POST请求参数必须用NameValuePair对象
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("b", Integer.toString(board_id)));
        params.add(new BasicNameValuePair("a", author));
        params.add(new BasicNameValuePair("t", title));
        params.add(new BasicNameValuePair("tag", tag));
        params.add(new BasicNameValuePair("c", content));
        params.add(new BasicNameValuePair("h", Integer.toString(head_index)));
        params.add(new BasicNameValuePair("l", link_url));

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

    public static String httpPostReply(int message_id,String author,String content,int head_index){
        String result = "" ;
        // 第一步，创建HttpPost对象
        HttpPost httpPost = new HttpPost( host + "/api2/movie/update_replies" );

        // 设置HTTP POST请求参数必须用NameValuePair对象
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("m_id", Integer.toString(message_id)));
        params.add(new BasicNameValuePair("a", author));
        params.add(new BasicNameValuePair("c", content));
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
