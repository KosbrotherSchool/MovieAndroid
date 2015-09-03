package com.jasonko.movietime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jasonko.movietime.adapters.YoutubeColumnAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.MyYoutubeColumn;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/28/15.
 */
public class RecommendColumnActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MyYoutubeColumn> mColumns;
    private YoutubeColumnAdapter youtubeColumnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_column);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("選擇電影專欄");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_fragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        new NewsTask().execute();
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            mColumns  = MovieAPI.getYoutubeColumns(1);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            youtubeColumnAdapter = new YoutubeColumnAdapter(RecommendColumnActivity.this, mColumns);
            recyclerView.setAdapter(youtubeColumnAdapter);
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