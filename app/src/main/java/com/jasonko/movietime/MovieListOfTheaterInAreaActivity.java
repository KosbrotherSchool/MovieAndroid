package com.jasonko.movietime;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private ImageView iv_movie_cover;



    private RecyclerView mRecyclerView;
    private MoviesListOfTheaterAdapter mAdapter;
    private ArrayList<MovieTime> mData;
    private ArrayList<MovieTime> testData = new ArrayList<MovieTime>();
    int theater_id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_of_theater_in_area);


        //set views
        tv_name_of_theater = (TextView)findViewById(R.id.tv_name_of_theater);
        tv_phone_of_theater = (TextView)findViewById(R.id.tv_phone_of_theater);
        tv_address_of_theater = (TextView)findViewById(R.id.tv_address_of_theater);
        iv_movie_cover = (ImageView)findViewById(R.id.iv_movie_cover);


        //set basic data of the clicked theater
        Intent intent = getIntent();
        tv_name_of_theater.setText(intent.getStringExtra("theater_name"));
        tv_phone_of_theater.setText(intent.getStringExtra("theater_phone"));
        tv_address_of_theater.setText(intent.getStringExtra("theater_address"));
        theater_id = intent.getIntExtra("theater_id", 0);




        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_movielist_of_theater);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        new GetMoviesTask().execute();

    }


    private class GetMoviesTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params){
            mData = MovieAPI.getTheaterMovieTimes(theater_id);
            return null;
        }

        @Override
        protected void onPostExecute(Object result){
            mAdapter = new MoviesListOfTheaterAdapter(MovieListOfTheaterInAreaActivity.this, mData);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
