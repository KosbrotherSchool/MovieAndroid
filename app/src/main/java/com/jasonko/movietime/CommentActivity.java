package com.jasonko.movietime;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.model.Comments;
import com.jasonko.movietime.model.Theater;

import java.util.ArrayList;


public class CommentActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Comments> mData;
    private LayoutInflater layoutInflater;
    private Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_comment);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("影片評論");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_comments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        CommentsListAdapter mAdapter = new CommentsListAdapter(mainActivity, mData);
        mRecyclerView.setAdapter(mAdapter);

    }


    class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ViewHolder> {

        public CommentsListAdapter(Activity activity, ArrayList<Comments> data) {
            mainActivity = activity;
            layoutInflater = LayoutInflater.from(activity);
            mData = data;

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View arg0) {
                super(arg0);
                mView = arg0;
            }

            View mView;

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = layoutInflater.inflate(R.layout.item_user_comment, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {


        }


    }
}
