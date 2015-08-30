package com.jasonko.movietime.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.adapters.MovieGridAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Movie;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/28/15.
 */
public class MovieGridFragment extends Fragment {

    public static final String ARG_MOVIE_ROUND = "MOVIE_ROUND_ID";
    private int movieRoundID;

    private ArrayList<Movie> mMovies;
    private GridView mGridView;
    private MovieGridAdapter movieGridAdapter;

    public static MovieGridFragment newInstance(int movie_round) {
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ROUND, movie_round);
        MovieGridFragment fragment = new MovieGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieRoundID = getArguments().getInt(ARG_MOVIE_ROUND);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);

        mGridView = (GridView) view.findViewById(R.id.fragment_gridview);

        if (movieGridAdapter != null){
            mGridView.setAdapter(movieGridAdapter);
        }else {
            new NewsTask().execute();
        }
        return view;
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            mMovies = MovieAPI.getMoviesByRoundID(movieRoundID);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            movieGridAdapter = new MovieGridAdapter(getActivity(), mMovies);
            mGridView.setAdapter(movieGridAdapter);
        }
    }
}