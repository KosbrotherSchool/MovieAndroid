package com.jasonko.movietime;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.jasonko.movietime.adapters.MessageDetailAdapter;
import com.jasonko.movietime.api.MessageAPI;
import com.jasonko.movietime.model.Message;
import com.jasonko.movietime.model.Reply;

import java.util.ArrayList;

/**
 * Created by kolichung on 10/14/15.
 */
public class MessageDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageDetailAdapter messageDetailAdapter;
    private int message_id;
    private Message mMessage;
    private String board_title;
    private ArrayList<Reply> replies;
    private ProgressBar progressBar;
    private static final int  write_reply_code  = 999;
    private boolean isNeedReload = false;
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        message_id = getIntent().getIntExtra("message id",0);
        mMessage = (Message) getIntent().getExtras().getSerializable("TheMessage");
        board_title = getIntent().getStringExtra("board_title");

        progressBar = (ProgressBar) findViewById(R.id.my_progress_bar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_fragment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(board_title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        new NewsTask().execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (messageDetailAdapter == null) {
            new NewsTask().execute();
        }else {
            if (isNeedReload) {
                replies.clear();
                new NewsTask().execute();
            }
        }
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            replies  = MessageAPI.getReplies(message_id,mPage);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (mMessage!=null) {
                messageDetailAdapter = new MessageDetailAdapter(MessageDetailActivity.this,mMessage, replies);
                recyclerView.setAdapter(messageDetailAdapter);
            }
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            if (isNeedReload == true){
                recyclerView.getLayoutManager().scrollToPosition(messageDetailAdapter.getItemCount()-1);
                isNeedReload = false;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reply, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }else if (menuItem.getItemId() == R.id.action_reply){
            Intent intent = new Intent(MessageDetailActivity.this, WriteReplyActivity.class);
            intent.putExtra("title", mMessage.getTitle());
            intent.putExtra("message_id", mMessage.getMessage_id());
            startActivityForResult(intent, write_reply_code);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case write_reply_code:
                isNeedReload = true;
                break;
            default:
                isNeedReload = false;
                break;
        }
    }

}