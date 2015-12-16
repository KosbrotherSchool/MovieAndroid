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

import com.jasonko.movietime.adapters.FavoriteMessageAdapter;
import com.jasonko.movietime.adapters.MessagesAdapter;
import com.jasonko.movietime.api.MessageAPI;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.FavoriteMessageDao;
import com.jasonko.movietime.model.Message;
import com.jasonko.movietime.tool.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolichung on 10/14/15.
 */
public class MessageBoardActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Message> mMessages = new ArrayList<>();
    private MessagesAdapter mAdapter;
    private int mPage = 1;

    private static final int  write_code  = 0;
    private boolean isNeedReload = false;

    private ProgressBar mProgressBar;
    private int board_id;
    private DaoMaster.DevOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        helper = new DaoMaster.DevOpenHelper(this, "expense", null);
        board_id = getIntent().getIntExtra("board_id",0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_comment);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        switch (board_id){
            case 0:
                getSupportActionBar().setTitle("公告區");
                break;
            case 1:
                getSupportActionBar().setTitle("電影版");
                break;
            case 2:
                getSupportActionBar().setTitle("戲劇版");
                break;
            case 3:
                getSupportActionBar().setTitle("生活版");
                break;
            case 4:
                getSupportActionBar().setTitle("最近按讚");
                break;
        }


        mProgressBar = (ProgressBar) findViewById(R.id.my_progress_bar);
        mProgressBar.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_comments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        if(board_id!=4) {
            mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    new CommentTask().execute();
                }
            });
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter == null) {
            if (board_id!=4) {
                new CommentTask().execute();
            }else{
                DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
                DaoSession daoSession = daoMaster.newSession();
                FavoriteMessageDao likeMessageDao = daoSession.getFavoriteMessageDao();
                List favoriteMessageList = likeMessageDao.queryBuilder().orderDesc(FavoriteMessageDao.Properties.Id).list();
                FavoriteMessageAdapter adapter = new FavoriteMessageAdapter(this,favoriteMessageList,4);
                mRecyclerView.setAdapter(adapter);
            }
        }else {
            if (isNeedReload) {
                mMessages.clear();
                mAdapter.notifyDataSetChanged();
                mPage = 1;
                new CommentTask().execute();
                isNeedReload = false;
            }else {
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private class CommentTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object[] params){
            ArrayList<Message> tempMessages = MessageAPI.getMessages(board_id,mPage);
            if(tempMessages != null && tempMessages.size() > 0){
                mMessages.addAll(tempMessages);
                mPage++;
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Object result){
            if((boolean) result){
                if(mAdapter == null){
                    mAdapter = new MessagesAdapter(MessageBoardActivity.this, mMessages, board_id);
                    mRecyclerView.setAdapter(mAdapter);
                }else {
                    mAdapter.notifyDataSetChanged();
                }
            }else{
                if (mAdapter != null) {
//                    Toast.makeText(MessageBoardActivity.this, "沒有其他留言", Toast.LENGTH_SHORT).show();
                }
            }
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(board_id!=4) {
            getMenuInflater().inflate(R.menu.menu_message, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }else if (menuItem.getItemId() == R.id.action_write_message){
            Intent intent = new Intent(MessageBoardActivity.this, WriteMessageActivity.class);
            intent.putExtra("board_id",board_id);
            startActivityForResult(intent, write_code);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case write_code:
                isNeedReload = true;
                break;
            default:
                isNeedReload = false;
                break;
        }
    }

}