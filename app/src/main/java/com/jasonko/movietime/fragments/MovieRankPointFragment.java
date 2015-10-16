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
import android.widget.Toast;

import com.jasonko.movietime.R;
import com.jasonko.movietime.adapters.RankMoviePointAdapter;
import com.jasonko.movietime.api.RankAPI;
import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.tool.EndlessRecyclerOnScrollListener;
import com.jasonko.movietime.tool.NetworkUtil;

import java.util.ArrayList;

/**
 * Created by kolichung on 10/14/15.
 */
public class MovieRankPointFragment extends Fragment {

    private ArrayList<Movie> mMovies = new ArrayList<>();
    private RecyclerView moviesRecylerView;
    private RankMoviePointAdapter movieRankAdapter;

    private ProgressBar mProgressBar;
    private TextView noNetText;

    private int mPage = 1;

    public static MovieRankPointFragment newInstance() {
        MovieRankPointFragment fragment = new MovieRankPointFragment();
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

        moviesRecylerView = (RecyclerView) view.findViewById(R.id.recycler_fragment);
        moviesRecylerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        moviesRecylerView.setLayoutManager(mLayoutManager);


        moviesRecylerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                new NewsTask().execute();
            }
        });

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
        return view;
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            ArrayList<Movie> feedBackMovies = RankAPI.getMoivePointRank(mPage);
            if (feedBackMovies != null && feedBackMovies.size() > 0){
                mMovies.addAll(feedBackMovies);
            }else {
                return false;
            }
            mPage = mPage + 1;
            return true;
        }

        @Override
        protected void onPostExecute(Object result) {

            if ((boolean)result) {
                if (movieRankAdapter == null) {
                    movieRankAdapter = new RankMoviePointAdapter(getActivity(), mMovies);
                    moviesRecylerView.setAdapter(movieRankAdapter);
                    mProgressBar.setVisibility(View.GONE);
                }else {
                    movieRankAdapter.notifyDataSetChanged();
                }
            }else {
                Toast.makeText(getActivity(), "無其他資料", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
