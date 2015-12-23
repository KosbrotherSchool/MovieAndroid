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
import com.jasonko.movietime.adapters.NewsAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.MovieNews;
import com.jasonko.movietime.tool.EndlessRecyclerOnScrollListener;
import com.jasonko.movietime.tool.NetworkUtil;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/26/15.
 */
public class NewsFragment extends Fragment {
    public static final String ARG_NEWS = "NEWS_TYPE_ID";

    private int mNewsTypeID;
    private ArrayList<MovieNews> mNews = new ArrayList<>();
    private int mPage = 1;
    private RecyclerView newsRecylerView;
    private NewsAdapter videoAdapter;

    private ProgressBar mProgressBar;
    private TextView noNetText;

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
        mProgressBar = (ProgressBar) view.findViewById(R.id.my_progress_bar);
        noNetText = (TextView) view.findViewById(R.id.no_network_text);

        newsRecylerView = (RecyclerView) view.findViewById(R.id.recycler_fragment);
        newsRecylerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newsRecylerView.setLayoutManager(mLayoutManager);
        newsRecylerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                new NewsTask().execute();
            }
        });

        if (videoAdapter != null){
            newsRecylerView.setAdapter(videoAdapter);
            mProgressBar.setVisibility(View.GONE);
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
            ArrayList<MovieNews> feedBackNews = MovieAPI.getMovieNews(mNewsTypeID,mPage);
            if (feedBackNews != null && feedBackNews.size() > 0){
                mNews.addAll(feedBackNews);
            }else {
                return false;
            }
            mPage = mPage + 1;
            return true;
        }

        @Override
        protected void onPostExecute(Object result) {
            if ((boolean)result) {
                if (videoAdapter == null) {
                    videoAdapter = new NewsAdapter(getActivity(), mNews);
                    newsRecylerView.setAdapter(videoAdapter);
                    mProgressBar.setVisibility(View.GONE);
                }else {
                    videoAdapter.notifyDataSetChanged();
                }
            }else {
                Toast.makeText(getActivity(),"無其他資料", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
