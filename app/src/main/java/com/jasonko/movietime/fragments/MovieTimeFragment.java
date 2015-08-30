package com.jasonko.movietime.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jasonko.movietime.R;
import com.jasonko.movietime.adapters.MovieTimeAdapter;
import com.jasonko.movietime.adapters.MovieTimeAreaAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Area;
import com.jasonko.movietime.model.MovieTime;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/27/15.
 */
public class MovieTimeFragment extends Fragment {

    public static final String ARG_MOVIE_ID = "MOVIE_ID";
    public static final String ARG_AREA_ID = "AREA_ID";
    private int mMovieID;
    private int mAreaID;

    private ArrayList<MovieTime> mMovieTimes;
    private RecyclerView areaRecycler;
    private RecyclerView theaterRecycler;

    public static MovieTimeFragment newInstance(int movie_id, int area_id) {
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movie_id);
        args.putInt(ARG_AREA_ID, area_id);
        MovieTimeFragment fragment = new MovieTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieID = getArguments().getInt(ARG_MOVIE_ID);
        mAreaID = getArguments().getInt(ARG_AREA_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_time, container, false);
        areaRecycler = (RecyclerView) view.findViewById(R.id.movietime_area_recycler_view);
        theaterRecycler = (RecyclerView) view.findViewById(R.id.movietime_theater_recycler_view);

        areaRecycler.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        areaRecycler.setLayoutManager(mLayoutManager);

        theaterRecycler.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        theaterRecycler.setLayoutManager(mLayoutManager2);

        MovieTimeAreaAdapter areaAdapter = new MovieTimeAreaAdapter(Area.getAreas(), mAreaID, this);
        areaRecycler.setAdapter(areaAdapter);

        new getMovieTimesTask().execute();
        return view;
    }

    public void runGetMovieTimesTask(int area_id){
        mAreaID = area_id;
        new getMovieTimesTask().execute();
    }

    private class getMovieTimesTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            mMovieTimes = MovieAPI.getAreaMovieTimes(mMovieID, mAreaID);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            MovieTimeAdapter movieTimeAdapter = new MovieTimeAdapter(mMovieTimes,getActivity());
            theaterRecycler.setAdapter(movieTimeAdapter);
        }
    }
}