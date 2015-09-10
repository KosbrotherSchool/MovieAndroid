package com.jasonko.movietime.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.adapters.ThisWeekMovieAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.tool.NetworkUtil;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/28/15.
 */
public class MovieListFragment extends Fragment {

    private ArrayList<Movie> mMovies;
    private RecyclerView newsRecylerView;
    private ThisWeekMovieAdapter weekMovieAdapter;

    private ProgressBar mProgressBar;
    private TextView noNetText;

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.my_progress_bar);
        noNetText = (TextView) view.findViewById(R.id.no_network_text);

        newsRecylerView = (RecyclerView) view.findViewById(R.id.recycler_fragment);
        newsRecylerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newsRecylerView.setLayoutManager(mLayoutManager);

        if (weekMovieAdapter != null){
            newsRecylerView.setAdapter(weekMovieAdapter);
        }else {
            if (NetworkUtil.getConnectivityStatus(getActivity()) != 0) {
                new NewsTask().execute();
            }else {
                noNetText.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        }

        return view;
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            mMovies = MovieAPI.getMoviesByRoundID(4,1);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (mMovies != null && mMovies.size() > 0) {
                weekMovieAdapter = new ThisWeekMovieAdapter(getActivity(), mMovies);
                newsRecylerView.setAdapter(weekMovieAdapter);
                mProgressBar.setVisibility(View.GONE);
            }else {
                mProgressBar.setVisibility(View.GONE);
                noNetText.setVisibility(View.VISIBLE);
            }
        }
    }
}