package com.jasonko.movietime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonko.movietime.model.Theater;

import java.util.ArrayList;

/**
 * Created by larry on 2015/8/26.
 */
public class TheaterLatelyFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TheatersListAdapter mAdapter;
    private ArrayList<Theater> mData = new ArrayList<Theater>();
    View theaterView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        theaterView = inflater.inflate(R.layout.tab_theaters_lately, container, false);
        return theaterView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();


        SharedPreferences preferences = getActivity().getSharedPreferences("RECENTLY_THEATERS", Context.MODE_PRIVATE);
        int finalone = preferences.getInt("FINALONE", 0);
        int count = preferences.getInt("COUNT", 0);


        //取出最近被點擊的電影院的資料
        Theater tempTheater;
        if (count < 10) {
            for (int i = count; i >= 1; i--) {
                tempTheater = Theater.getTheaterByID(preferences.getInt("THEATER" + i, 0));
                mData.add(tempTheater);
            }
        } else if (count == 10) {
            for (int i = finalone; i >= 1; i--) {
                tempTheater = Theater.getTheaterByID(preferences.getInt("THEATER" + i, 0));
                mData.add(tempTheater);
            }
            for (int i = 10; i > finalone; i--) {
                tempTheater = Theater.getTheaterByID(preferences.getInt("THEATER" + i, 0));
                mData.add(tempTheater);
            }
        } else
            Log.d("TheaterLatelyFragment", "count not in 1~10");
        //電影院資料取得完畢

        mRecyclerView = (RecyclerView) theaterView.findViewById(R.id.recycler_view_theaters_lately);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new TheatersListAdapter(getActivity(), mData);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();







    }
}
