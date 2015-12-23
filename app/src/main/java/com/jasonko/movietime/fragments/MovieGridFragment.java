package com.jasonko.movietime.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.adapters.MovieGridAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.tool.EndlessScrollListener;
import com.jasonko.movietime.tool.NetworkUtil;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/28/15.
 */
public class MovieGridFragment extends Fragment {

    public static final String ARG_MOVIE_ROUND = "MOVIE_ROUND_ID";
    private int movieRoundID;
    private int mPage=1;

    private ArrayList<Movie> mMovies = new ArrayList<>();
    private GridView mGridView;
    private MovieGridAdapter movieGridAdapter;

    private ProgressBar mProgressBar;
    private TextView noNetText;

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
        mProgressBar = (ProgressBar) view.findViewById(R.id.my_progress_bar);
        mGridView = (GridView) view.findViewById(R.id.fragment_gridview);
        noNetText = (TextView) view.findViewById(R.id.no_network_text);

        mGridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if(movieRoundID!=4) {
                    new NewsTask().execute();
                }
            }
        });

        if (movieGridAdapter != null){
            mProgressBar.setVisibility(View.GONE);
            mGridView.setAdapter(movieGridAdapter);
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
            ArrayList<Movie> feedBackMovies = MovieAPI.getMoviesByRoundID(movieRoundID,mPage);
            if (feedBackMovies!= null && feedBackMovies.size()>0) {
                mMovies.addAll(feedBackMovies);
                mPage = mPage +1;
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Object result) {
            if ((boolean)result) {
                if (movieGridAdapter == null) {
                    movieGridAdapter = new MovieGridAdapter(getActivity(), mMovies, movieRoundID);
                    mGridView.setAdapter(movieGridAdapter);
                }else {
                    movieGridAdapter.notifyDataSetChanged();
                }
            }else {
//                Toast.makeText(getActivity(), "無其他資料", Toast.LENGTH_SHORT).show();
            }
            mProgressBar.setVisibility(View.GONE);
        }
    }
}