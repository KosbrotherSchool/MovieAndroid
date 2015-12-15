package com.jasonko.movietime.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.adapters.FavoriteTheaterAdapter;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.FavoriteTheater;
import com.jasonko.movietime.dao.FavoriteTheaterDao;

import java.util.ArrayList;

/**
 * Created by larry on 2015/8/26.
 */
public class FavoriteTheaterFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FavoriteTheaterAdapter mAdapter;
    public  static ArrayList<FavoriteTheater> mTheaters = new ArrayList<>();
    private View theaterView;
    private TextView tv_no_favorite_theater;


    public static FavoriteTheaterFragment newInstance() {
        FavoriteTheaterFragment fragment = new FavoriteTheaterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        theaterView = inflater.inflate(R.layout.fragment_favorite_theater, container, false);
        tv_no_favorite_theater = (TextView) theaterView.findViewById(R.id.tv_no_lately_theater);
        return theaterView;
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
        FavoriteTheaterDao theaterDao = daoSession.getFavoriteTheaterDao();
        mTheaters = (ArrayList) theaterDao.queryBuilder().orderDesc(FavoriteTheaterDao.Properties.Id).list();

        if (mTheaters.size() == 0){
            tv_no_favorite_theater.setVisibility(View.VISIBLE);
        }else {
            tv_no_favorite_theater.setVisibility(View.GONE);
        }

        mRecyclerView = (RecyclerView) theaterView.findViewById(R.id.recycler_view_theaters_lately);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new FavoriteTheaterAdapter(getActivity(), mTheaters);
        mRecyclerView.setAdapter(mAdapter);


    }


}
