package com.jasonko.movietime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.jasonko.movietime.adapters.TheaterGridAdapter;

/**
 * Created by kolichung on 8/26/15.
 */
public class TicketActivity extends AppCompatActivity {

    private GridView mGridView;
    private TheaterGridAdapter mTheaterGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        mGridView = (GridView) findViewById(R.id.fragment_gridview);
        mTheaterGridAdapter = new TheaterGridAdapter(TicketActivity.this, AppParams.orderTheaters);
        mGridView.setAdapter(mTheaterGridAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("線上訂票網站");

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