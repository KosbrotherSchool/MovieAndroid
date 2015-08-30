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
import com.jasonko.movietime.adapters.NewsAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.MovieNews;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/26/15.
 */
public class NewsFragment extends Fragment {
    public static final String ARG_NEWS = "NEWS_TYPE_ID";

    private int mNewsTypeID;
    private ArrayList<MovieNews> mNews;
    private int mPage = 1;
    private RecyclerView newsRecylerView;
    private NewsAdapter videoAdapter;

    public static NewsFragment newInstance(int news_type_id) {
        Bundle args = new Bundle();
        args.putInt(ARG_NEWS, news_type_id);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsTypeID = getArguments().getInt(ARG_NEWS);


    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        newsRecylerView = (RecyclerView) view.findViewById(R.id.recycler_fragment);
        newsRecylerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newsRecylerView.setLayoutManager(mLayoutManager);

        if (videoAdapter != null){
            newsRecylerView.setAdapter(videoAdapter);
        }else {
            new NewsTask().execute();
        }

        return view;
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            mNews = MovieAPI.getMovieNews(mNewsTypeID,mPage);
            mPage = mPage + 1;
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            videoAdapter = new NewsAdapter(getActivity(), mNews);
            newsRecylerView.setAdapter(videoAdapter);
        }
    }
}
