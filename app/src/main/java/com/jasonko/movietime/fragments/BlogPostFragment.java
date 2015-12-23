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
import com.jasonko.movietime.adapters.BlogPostsAdapter;
import com.jasonko.movietime.api.BlogAPI;
import com.jasonko.movietime.model.BlogPost;
import com.jasonko.movietime.tool.EndlessRecyclerOnScrollListener;
import com.jasonko.movietime.tool.NetworkUtil;

import java.util.ArrayList;

/**
 * Created by kolichung on 10/14/15.
 */
public class BlogPostFragment extends Fragment {

    private ArrayList<BlogPost> mPosts = new ArrayList<>();
    private int mPage = 1;
    private RecyclerView newsRecylerView;
    private BlogPostsAdapter postsAdapter;

    private ProgressBar mProgressBar;
    private TextView noNetText;

    public static BlogPostFragment newInstance() {
        BlogPostFragment fragment = new BlogPostFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

        if (postsAdapter != null){
            newsRecylerView.setAdapter(postsAdapter);
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
            ArrayList<BlogPost> feedBackPosts = BlogAPI.getBlogPost(mPage);
            if (feedBackPosts != null && feedBackPosts.size() > 0){
                mPosts.addAll(feedBackPosts);
            }else {
                return false;
            }
            mPage = mPage + 1;
            return true;
        }

        @Override
        protected void onPostExecute(Object result) {
            if ((boolean)result) {
                if (postsAdapter == null) {
                    postsAdapter = new BlogPostsAdapter(getActivity(), mPosts);
                    newsRecylerView.setAdapter(postsAdapter);
                    mProgressBar.setVisibility(View.GONE);
                }else {
                    postsAdapter.notifyDataSetChanged();
                }
            }else {
                Toast.makeText(getActivity(), "無其他資料", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
