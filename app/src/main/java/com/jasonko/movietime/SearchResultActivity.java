package com.jasonko.movietime;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonko.movietime.adapters.SearchMovieAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.tool.EndlessRecyclerOnScrollListener;
import com.jasonko.movietime.tool.NetworkUtil;
import com.quinny898.library.persistentsearch.SearchBox;

import java.util.ArrayList;

/**
 * Created by kolichung on 9/4/15.
 */
public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private SearchMovieAdapter searchMovieAdapter;
    private SearchBox searchBox;

    private int mPage = 1;
    private String queryString;
    private ProgressBar mProgressBar;
    private TextView noNetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        queryString = getIntent().getStringExtra("query");

        mProgressBar = (ProgressBar) findViewById(R.id.my_progress_bar);
        noNetText = (TextView) findViewById(R.id.no_network_text);
        searchBox = (SearchBox) findViewById(R.id.searchbox);
        setSearchBar();

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

        if (NetworkUtil.getConnectivityStatus(SearchResultActivity.this) != 0) {
            new NewsTask().execute();
        }else {
            mProgressBar.setVisibility(View.GONE);
            noNetText.setVisibility(View.VISIBLE);
        }
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            ArrayList<Movie> feedBackMovies = MovieAPI.getSearchedMovies(queryString, mPage);
            if (feedBackMovies != null && feedBackMovies.size() > 0) {
                mMovies.addAll(feedBackMovies);
                mPage = mPage + 1;
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Object result) {
            if ((boolean) result) {
                if (searchMovieAdapter == null) {
                    searchMovieAdapter = new SearchMovieAdapter(SearchResultActivity.this, mMovies);
                    recyclerView.setAdapter(searchMovieAdapter);
                }else {
                    searchMovieAdapter.notifyDataSetChanged();
                }
            }else {
                Toast.makeText(SearchResultActivity.this,"無其他資料", Toast.LENGTH_SHORT).show();
            }
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void setSearchBar(){
        searchBox = (SearchBox) findViewById(R.id.searchbox);
        searchBox.enableVoiceRecognition(this);
        searchBox.setLogoText(queryString);
        searchBox.setMenuListener(new SearchBox.MenuListener() {
            @Override
            public void onMenuClick() {
                finish();
            }

        });
        searchBox.setSearchListener(new SearchBox.SearchListener() {

            @Override
            public void onSearchOpened() {
            }

            @Override
            public void onSearchClosed() {
            }

            @Override
            public void onSearchTermChanged() {
            }

            @Override
            public void onSearch(String searchTerm) {
//                Toast.makeText(SearchResultActivity.this, searchTerm + " Searched", Toast.LENGTH_LONG).show();
                queryString = searchTerm;
                searchBox.setLogoText(queryString);
                mMovies.clear();
//                searchMovieAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.VISIBLE);
                mPage = 1;
                new NewsTask().execute();

            }

            @Override
            public void onSearchCleared() {

            }

        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isAdded() && requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == this.RESULT_OK) {
            // get first match and move to search result activity
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            queryString = matches.get(0);
            searchBox.setLogoText(queryString);
            mMovies.clear();
//            searchMovieAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.VISIBLE);
            mPage = 1;
            new NewsTask().execute();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isAdded() {
        searchBox.clearResults();
        return true;
    }


}