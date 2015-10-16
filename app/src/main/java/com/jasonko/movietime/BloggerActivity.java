package com.jasonko.movietime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.jasonko.movietime.adapters.BloggerGridAdapterTwo;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Blogger;

import java.util.ArrayList;

/**
 * Created by kolichung on 9/23/15.
 */
public class BloggerActivity extends AppCompatActivity {

    private GridView mGridView;
    private BloggerGridAdapterTwo mBloggerGridAdatper;
    private ArrayList<Blogger> bloggers;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogger);
        mGridView = (GridView) findViewById(R.id.fragment_gridview);
        mProgressBar = (ProgressBar) findViewById(R.id.my_progress_bar);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setBackgroundResource(R.color.deep_green);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("電影部落格");

        new MovieBlogsTask().execute();
    }


    private class MovieBlogsTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            bloggers = MovieAPI.getBlogs();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            mProgressBar.setVisibility(View.GONE);
            if (bloggers != null && bloggers.size() > 0) {
                mBloggerGridAdatper = new BloggerGridAdapterTwo(BloggerActivity.this, bloggers);
                mGridView.setAdapter(mBloggerGridAdatper);
            }else{

            }
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