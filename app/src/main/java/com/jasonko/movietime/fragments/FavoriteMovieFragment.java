package com.jasonko.movietime.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.adapters.FollowMovieGridAdapter;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.FollowMovie;
import com.jasonko.movietime.dao.FollowMovieDao;

import java.util.ArrayList;

/**
 * Created by kolichung on 10/21/15.
 */
public class FavoriteMovieFragment extends Fragment {

    private GridView mGridView;
    private FollowMovieGridAdapter followMovieGridAdapter;
    private ArrayList<FollowMovie> mMovies = new ArrayList<>();
    private TextView noNetText;

    public static FavoriteMovieFragment newInstance() {
        FavoriteMovieFragment fragment = new FavoriteMovieFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        mGridView = (GridView) view.findViewById(R.id.fragment_gridview);
        ProgressBar mProgressBar = (ProgressBar) view.findViewById(R.id.my_progress_bar);
        mProgressBar.setVisibility(View.GONE);
        noNetText = (TextView) view.findViewById(R.id.no_network_text);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "expense", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FollowMovieDao movieDao = daoSession.getFollowMovieDao();

        mMovies = (ArrayList) movieDao.queryBuilder().orderDesc(FollowMovieDao.Properties.Update_date).list();
        if (mMovies.size()>0) {
            noNetText.setVisibility(View.GONE);
            followMovieGridAdapter = new FollowMovieGridAdapter(getActivity(), mMovies);
            mGridView.setAdapter(followMovieGridAdapter);
        }else {
            noNetText.setText("尚未有影片加入我的最愛");
            noNetText.setVisibility(View.VISIBLE);
        }



    }


}