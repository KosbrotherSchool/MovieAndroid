package com.jasonko.movietime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.jasonko.movietime.adapters.TrailerGridAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Trailer;

import java.util.ArrayList;

/**
 * Created by kolichung on 9/3/15.
 */
public class TrailersActivity extends AppCompatActivity {

    private GridView mGridView;
    private ArrayList<Trailer> mTrailers;
    private int movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);
        String movie_title = getIntent().getStringExtra("movie_title");
        movie_id = getIntent().getIntExtra("movie_id",0);

        mGridView = (GridView) findViewById(R.id.activity_gridview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(movie_title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        new NewsTask().execute();
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            mTrailers  = MovieAPI.getMovieTrailersByID(movie_id);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            TrailerGridAdapter adapter = new TrailerGridAdapter(TrailersActivity.this, mTrailers);
            mGridView.setAdapter(adapter);
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