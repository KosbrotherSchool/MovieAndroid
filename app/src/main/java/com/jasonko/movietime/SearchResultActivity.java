package com.jasonko.movietime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jasonko.movietime.adapters.SearchMovieAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.tool.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

/**
 * Created by kolichung on 9/4/15.
 */
public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private SearchMovieAdapter searchMovieAdapter;

    private int mPage = 1;
    private String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        queryString = getIntent().getStringExtra("query");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_search_result);
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
            mMovies = MovieAPI.getSearchedMovies(queryString, mPage);
            return false;
        }

        @Override
        protected void onPostExecute(Object result) {
            searchMovieAdapter = new SearchMovieAdapter(SearchResultActivity.this, mMovies);
            recyclerView.setAdapter(searchMovieAdapter);
        }
    }


}