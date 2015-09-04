package com.jasonko.movietime;

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
import android.widget.Toast;

import com.jasonko.movietime.adapters.YoutubeColumnAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.MyYoutubeColumn;
import com.jasonko.movietime.tool.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/28/15.
 */
public class RecommendColumnActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MyYoutubeColumn> mColumns = new ArrayList<>();
    private YoutubeColumnAdapter youtubeColumnAdapter;

    private ProgressBar mProgressBar;
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_column);
        mProgressBar = (ProgressBar) findViewById(R.id.my_progress_bar);

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
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                new NewsTask().execute();
            }
        });

        new NewsTask().execute();
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            ArrayList<MyYoutubeColumn> feedBackColumns = MovieAPI.getYoutubeColumns(mPage);
            if (feedBackColumns != null && feedBackColumns.size()>0){
                mColumns.addAll(feedBackColumns);
                mPage = mPage + 1;
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Object result) {
            if ((boolean) result){
                if (youtubeColumnAdapter == null) {
                    youtubeColumnAdapter = new YoutubeColumnAdapter(RecommendColumnActivity.this, mColumns);
                    recyclerView.setAdapter(youtubeColumnAdapter);
                    mProgressBar.setVisibility(View.GONE);
                }else {
                    youtubeColumnAdapter.notifyDataSetChanged();
                }
            }else {
                Toast.makeText(RecommendColumnActivity.this, "無其他資料", Toast.LENGTH_SHORT).show();
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