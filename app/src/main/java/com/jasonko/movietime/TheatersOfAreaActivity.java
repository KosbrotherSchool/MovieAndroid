package com.jasonko.movietime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jasonko.movietime.adapters.TheatersListAdapter;
import com.jasonko.movietime.model.Theater;

import java.util.ArrayList;


public class TheatersOfAreaActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TheatersListAdapter mAdapter;
    private ArrayList<Theater> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theaters_of_area);

        Intent intent = getIntent();
        //getIntExtra的第二個參數指的是，若取不到數值，就以第二個參數當預設值
        int area_id = intent.getIntExtra("area_id", 0);
        String area_name = intent.getStringExtra("area_name");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(area_name);


        //設定Adapter
        if (area_id != 25) {
            mData = Theater.getTheaters(area_id);
        }else {
            mData = Theater.getSecondRoundTheaters();
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_theaters_of_area);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new TheatersListAdapter(this, mData);
        mAdapter.setOnItemClickListener(new TheatersListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Theater theater = mData.get(position);
                Intent intent1 = new Intent(TheatersOfAreaActivity.this, TheaterActivity.class);
                intent1.putExtra("theater_name", theater.getName());
                intent1.putExtra("theater_phone", theater.getPhone());
                intent1.putExtra("theater_address", theater.getAddress());
                intent1.putExtra("theater_id", theater.getTheater_id());

                startActivity(intent1);

            }
        });

        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


}
