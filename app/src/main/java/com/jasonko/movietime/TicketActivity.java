package com.jasonko.movietime;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.GridView;

import com.jasonko.movietime.adapters.TheaterGridAdapter;

/**
 * Created by kolichung on 8/26/15.
 */
public class TicketActivity extends FragmentActivity {

    private GridView mGridView;
    private TheaterGridAdapter mTheaterGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_grid);
        mGridView = (GridView) findViewById(R.id.fragment_gridview);
        mTheaterGridAdapter = new TheaterGridAdapter(TicketActivity.this, AppParams.orderTheaters);
        mGridView.setAdapter(mTheaterGridAdapter);

    }


}