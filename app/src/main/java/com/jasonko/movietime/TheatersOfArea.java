package com.jasonko.movietime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jasonko.movietime.model.Theater;

import java.util.ArrayList;


public class TheatersOfArea extends Activity {

    private RecyclerView mRecyclerView;
    private TheatersListAdapter mAdapter;
    private ArrayList<Theater> mData;
    private TextView tv_area_of_theaters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theaters_of_area);

        tv_area_of_theaters = (TextView)findViewById(R.id.tv_area_of_theaters);

        Intent intent = getIntent();
        //getIntExtra的第二個參數指的是，若取不到數值，就以第二個參數當預設值
        int area_id = intent.getIntExtra("area_id", 0);
        String area_name = intent.getStringExtra("area_name");
        tv_area_of_theaters.setText(area_name);


        //設定Adapter
        mData = Theater.getTheaters(area_id);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_theaters_of_area);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new TheatersListAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);


    }


}
