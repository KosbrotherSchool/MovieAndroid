package com.jasonko.movietime.fragments;

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

import com.jasonko.movietime.R;
import com.jasonko.movietime.adapters.TheatersListAdapter;
import com.jasonko.movietime.model.Theater;

import java.util.ArrayList;

/**
 * Created by larry on 2015/8/26.
 */
public class TheaterLatelyFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TheatersListAdapter mAdapter;
    public  static ArrayList<Theater> mData = new ArrayList<Theater>();
    private View theaterView;
    private TextView tv_no_lately_theater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        theaterView = inflater.inflate(R.layout.tab_theaters_lately, container, false);
        tv_no_lately_theater = (TextView) theaterView.findViewById(R.id.tv_no_lately_theater);
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


        //check if there is some lately theater
        if(count == 0)
            tv_no_lately_theater.setText("您還未瀏覽過任何影院");
        else {
            tv_no_lately_theater.setText("");
            tv_no_lately_theater.setTextSize(0);
        }

        mData.clear();

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









    }


}
