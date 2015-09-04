package com.jasonko.movietime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonko.movietime.adapters.MoviesListOfTheaterAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.MovieTime;

import java.util.ArrayList;


public class MovieListOfTheaterInAreaActivity extends Activity {

    private TextView tv_name_of_theater;
    private TextView tv_phone_of_theater;
    private TextView tv_address_of_theater;



    private RecyclerView mRecyclerView;
    private MoviesListOfTheaterAdapter mAdapter;
    private ArrayList<MovieTime> mData;
    private ArrayList<MovieTime> testData = new ArrayList<MovieTime>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_of_theater_in_area);


        //set views
        tv_name_of_theater = (TextView)findViewById(R.id.tv_name_of_theater);
        tv_phone_of_theater = (TextView)findViewById(R.id.tv_phone_of_theater);
        tv_address_of_theater = (TextView)findViewById(R.id.tv_address_of_theater);


        //set basic data of the clicked theater
        Intent intent = getIntent();
        tv_name_of_theater.setText(intent.getStringExtra("theater_name"));
        tv_phone_of_theater.setText(intent.getStringExtra("theater_phone"));
        tv_address_of_theater.setText(intent.getStringExtra("theater_address"));
        int theater_id = intent.getIntExtra("theater_id", 0);


        Log.d("MovieListActivity", "before MovieAPI");
        //set RecyclerView & Adapter
        mData = MovieAPI.getTheaterMovieTimes(theater_id) ;

        Log.d("MovieListActivity", "after MovieAPI");

        if(mData == null)
            Toast.makeText(MovieListOfTheaterInAreaActivity.this, "null pointer", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(MovieListOfTheaterInAreaActivity.this, "not null pointer", Toast.LENGTH_SHORT).show();
            /*
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_movielist_of_theater);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);

            mAdapter = new MoviesListOfTheaterAdapter(MovieListOfTheaterInAreaActivity.this, mData);
            mRecyclerView.setAdapter(mAdapter);
            */
        }

    }


}
