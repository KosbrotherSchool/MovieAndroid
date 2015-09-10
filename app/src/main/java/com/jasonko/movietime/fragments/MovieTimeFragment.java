package com.jasonko.movietime.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.adapters.MovieTimeAdapter;
import com.jasonko.movietime.adapters.MovieTimeAreaAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Area;
import com.jasonko.movietime.model.MovieTime;
import com.jasonko.movietime.tool.NetworkUtil;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/27/15.
 */
public class MovieTimeFragment extends Fragment {

    public static final String ARG_MOVIE_ID = "MOVIE_ID";

    private int mMovieID;
    private int mAreaID;

    private ArrayList<MovieTime> mMovieTimes;
    private ArrayList<Area> mAreas;
    private RecyclerView areaRecycler;
    private RecyclerView theaterRecycler;
    private TextView noTheaterText;
    private TextView noNetText;

    public static MovieTimeFragment newInstance(int movie_id) {
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movie_id);
        MovieTimeFragment fragment = new MovieTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieID = getArguments().getInt(ARG_MOVIE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_time, container, false);
        areaRecycler = (RecyclerView) view.findViewById(R.id.movietime_area_recycler_view);
        theaterRecycler = (RecyclerView) view.findViewById(R.id.movietime_theater_recycler_view);
        noTheaterText = (TextView) view.findViewById(R.id.movietime_no_theater_text);
        noNetText = (TextView) view.findViewById(R.id.no_network_text);

        areaRecycler.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        areaRecycler.setLayoutManager(mLayoutManager);

        theaterRecycler.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        theaterRecycler.setLayoutManager(mLayoutManager2);


        if (NetworkUtil.getConnectivityStatus(getActivity()) != 0 ) {
            new getMovieAreaTask().execute();
        }else {
            noNetText.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void runGetMovieTimesTask(int area_id){
        mAreaID = area_id;
        new getMovieTimesTask().execute();
    }


    private class getMovieAreaTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            mAreas = MovieAPI.getMovieAreasByMovieID(mMovieID);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (mAreas != null && mAreas.size() > 0) {
                mAreaID = mAreas.get(0).getArea_id();
                MovieTimeAreaAdapter areaAdapter = new MovieTimeAreaAdapter(mAreas, mAreas.get(0).getArea_id(), MovieTimeFragment.this);
                areaRecycler.setAdapter(areaAdapter);
                new getMovieTimesTask().execute();
            }else {
                noTheaterText.setVisibility(View.VISIBLE);
                areaRecycler.setBackgroundResource(R.color.background_color);
            }
        }
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