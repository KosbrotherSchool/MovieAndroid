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
import com.jasonko.movietime.adapters.RankMovieExpectAdpter;
import com.jasonko.movietime.adapters.RankMovieHorizontalAdapter;
import com.jasonko.movietime.adapters.RankMovieSatisfyAdapter;
import com.jasonko.movietime.adapters.RankMovieWeekAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.tool.NetworkUtil;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/28/15.
 */
public class MovieRankFragment extends Fragment {
    public static final String ARG_RANK_TYPE = "RANK_TYPE";

    private int mRankTypeID;
    private ArrayList<Movie> mMovies;
    private RecyclerView moviesRecylerView;
    private RankMovieHorizontalAdapter movieRankAdapter;
    private RankMovieWeekAdapter movieWeekAdapter;
    private RankMovieExpectAdpter movieExpectAdpter;
    private RankMovieSatisfyAdapter movieSatisfyAdapter;

    private ProgressBar mProgressBar;
    private TextView noNetText;

    public static MovieRankFragment newInstance(int rank_type_id) {
        Bundle args = new Bundle();
        args.putInt(ARG_RANK_TYPE, rank_type_id);
        MovieRankFragment fragment = new MovieRankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRankTypeID = getArguments().getInt(ARG_RANK_TYPE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.my_progress_bar);
        noNetText = (TextView) view.findViewById(R.id.no_network_text);

        moviesRecylerView = (RecyclerView) view.findViewById(R.id.recycler_fragment);
        moviesRecylerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        moviesRecylerView.setLayoutManager(mLayoutManager);

        switch (mRankTypeID){
            case 1:
                if (movieRankAdapter != null){
                    moviesRecylerView.setAdapter(movieRankAdapter);
                }else {
                    if (NetworkUtil.getConnectivityStatus(getActivity()) != 0) {
                        new NewsTask().execute();
                    }else {
                        mProgressBar.setVisibility(View.GONE);
                        noNetText.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 2:
                if (movieRankAdapter != null){
                    moviesRecylerView.setAdapter(movieRankAdapter);
                }else {
                    if (NetworkUtil.getConnectivityStatus(getActivity()) != 0) {
                        new NewsTask().execute();
                    }else {
                        mProgressBar.setVisibility(View.GONE);
                        noNetText.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 3:
                if (movieWeekAdapter != null){
                    moviesRecylerView.setAdapter(movieWeekAdapter);
                }else {
                    if (NetworkUtil.getConnectivityStatus(getActivity()) != 0) {
                        new NewsTask().execute();
                    }else {
                        mProgressBar.setVisibility(View.GONE);
                        noNetText.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 5:
                if (movieExpectAdpter != null){
                    moviesRecylerView.setAdapter(movieExpectAdpter);
                }else {
                    if (NetworkUtil.getConnectivityStatus(getActivity()) != 0) {
                        new NewsTask().execute();
                    }else {
                        mProgressBar.setVisibility(View.GONE);
                        noNetText.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 6:
                if (movieSatisfyAdapter != null){
                    moviesRecylerView.setAdapter(movieSatisfyAdapter);
                }else {
                    if (NetworkUtil.getConnectivityStatus(getActivity()) != 0) {
                        new NewsTask().execute();
                    }else {
                        mProgressBar.setVisibility(View.GONE);
                        noNetText.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }


        return view;
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            switch (mRankTypeID){
                case 1:
                    mMovies = MovieAPI.getTaipeiRankMovies(-1);
                    break;
                case 2:
                    mMovies = MovieAPI.getUSRankMovies();
                    break;
                case 3:
                    mMovies = MovieAPI.getWeekRankMovies();
                    break;
                case 5:
                    mMovies = MovieAPI.getExpectRankMovies();
                    break;
                case 6:
                    mMovies = MovieAPI.getSatisfyRankMovies();
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {

            if (mMovies != null && mMovies.size() > 0) {
                switch (mRankTypeID) {
                    case 1:
                        movieRankAdapter = new RankMovieHorizontalAdapter(getActivity(), mMovies);
                        moviesRecylerView.setAdapter(movieRankAdapter);
                        break;
                    case 2:
                        movieRankAdapter = new RankMovieHorizontalAdapter(getActivity(), mMovies);
                        moviesRecylerView.setAdapter(movieRankAdapter);
                        break;
                    case 3:
                        movieWeekAdapter = new RankMovieWeekAdapter(getActivity(), mMovies);
                        moviesRecylerView.setAdapter(movieWeekAdapter);
                        break;
                    case 5:
                        movieExpectAdpter = new RankMovieExpectAdpter(getActivity(), mMovies);
                        moviesRecylerView.setAdapter(movieExpectAdpter);
                        break;
                    case 6:
                        movieSatisfyAdapter = new RankMovieSatisfyAdapter(getActivity(), mMovies);
                        moviesRecylerView.setAdapter(movieSatisfyAdapter);
                        break;
                }
                mProgressBar.setVisibility(View.GONE);
            }else {
                mProgressBar.setVisibility(View.GONE);
                noNetText.setVisibility(View.VISIBLE);
            }

        }
    }
}