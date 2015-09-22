package com.jasonko.movietime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jasonko.movietime.adapters.MoviesListOfTheaterAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.MovieTime;

import java.util.ArrayList;


public class MovieListOfTheaterInAreaActivity extends AppCompatActivity {

    private TextView tv_phone_of_theater;
    private TextView tv_address_of_theater;
    private String stringAddress;

    private RecyclerView mRecyclerView;
    private MoviesListOfTheaterAdapter mAdapter;
    private ArrayList<MovieTime> mData;
    int theater_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_of_theater_in_area);

        //set views
        tv_phone_of_theater = (TextView)findViewById(R.id.tv_phone_of_theater);
        tv_address_of_theater = (TextView)findViewById(R.id.tv_address_of_theater);


        //set basic data of the clicked theater
        Intent intent = getIntent();
        tv_phone_of_theater.setText(intent.getStringExtra("theater_phone"));
        tv_address_of_theater.setText(intent.getStringExtra("theater_address"));
        theater_id = intent.getIntExtra("theater_id", 0);

        stringAddress = intent.getStringExtra("theater_address");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(intent.getStringExtra("theater_name"));

        //save the theater_id when any theater is viewed
        ////////////////////////////////////////////////
        SharedPreferences preferences = getSharedPreferences("RECENTLY_THEATERS", Context.MODE_PRIVATE);
        int count = preferences.getInt("COUNT", 0);
        int finalone = preferences.getInt("FINALONE", 1);

        if(count>0){
            //check if the clicked theater is in the list already
            Boolean notInList = true;
            for(int i=1;i<=count; i++){
                int id = theater_id;
                if(theater_id == preferences.getInt("THEATER"+i, 0)){
                    notInList = false;

                    if(i == finalone){

                    }
                    else if(i > finalone){
                        int j = i -1;
                        int tmp_id;
                        while(j>finalone){
                            tmp_id = preferences.getInt("THEATER"+j, 0);
                            preferences.edit().putInt("THEATER"+(j+1), tmp_id).commit();
                            j--;
                        }
                        finalone++;
                        preferences.edit().putInt("THEATER"+finalone, id)
                                .putInt("FINALONE", finalone).commit();

                    }
                    else if(i <finalone){
                        int j = i+1;
                        int tmp_id;
                        while(j<=finalone){
                            tmp_id = preferences.getInt("THEATER"+j, 0);
                            preferences.edit().putInt("THEATER"+ (j-1), tmp_id).commit();
                            j++;
                        }

                        preferences.edit().putInt("THEATER"+finalone, id).commit();
                    }

                    break;}
            }

            //if in list already, do nothing.
            //if not in list, add it
            if(notInList){
                if(count<10){
                    finalone++;
                    preferences.edit()
                            .putInt("COUNT", count +1)
                            .putInt("FINALONE", finalone)
                            .putInt("THEATER"+finalone, theater_id).apply();
                }
                else if(count == 10){
                    if(finalone == 10) {
                        finalone = 1;
                    }
                    else {
                        finalone++;
                    }

                    preferences.edit()
                            .putInt("FINALONE", finalone)
                            .putInt("THEATER"+finalone, theater_id).apply();
                }
            }

        }
        else{
            preferences.edit()
                    .putInt("THEATER1", theater_id)
                    .putInt("COUNT", 1).apply();
        }

        //the saving is done
        ////////////////////////////////////////////////
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_theater, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_to_map:
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=" + stringAddress));
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
