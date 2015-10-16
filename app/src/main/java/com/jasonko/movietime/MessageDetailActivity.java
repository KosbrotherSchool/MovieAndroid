package com.jasonko.movietime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jasonko.movietime.api.MessageAPI;
import com.jasonko.movietime.model.Message;

/**
 * Created by kolichung on 10/14/15.
 */
public class MessageDetailActivity extends AppCompatActivity {

    TextView author;
    TextView title;
    TextView publish_date;
    TextView messageTag;
    TextView viewCount;
    TextView content;

    int message_id;
    Message mMessage;

    LinearLayout layoutContent;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        message_id = getIntent().getIntExtra("message id",0);

        author = (TextView) findViewById(R.id.text_userId);
        title = (TextView) findViewById(R.id.text_message_title);
        publish_date = (TextView) findViewById(R.id.text_publish_date);
        messageTag = (TextView) findViewById(R.id.message_tag_text);
        viewCount = (TextView) findViewById(R.id.text_message_viewcount);
        content = (TextView) findViewById(R.id.message_tontent_text);
        layoutContent = (LinearLayout) findViewById(R.id.layout_message);
        progressBar = (ProgressBar) findViewById(R.id.my_progress_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("留言內容");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        new NewsTask().execute();
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            mMessage  = MessageAPI.getMessageByID(message_id);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (mMessage!=null) {
                layoutContent.setVisibility(View.VISIBLE);
                author.setText("作者:" + mMessage.getAuthor());
                title.setText(mMessage.getTitle());
                publish_date.setText(mMessage.getPub_date());
                messageTag.setText("[" + mMessage.getMessage_tag() + "]");
                viewCount.setText(Integer.toString(mMessage.getView_count()) + "人瀏覽");
                content.setText(mMessage.getContent());
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

}