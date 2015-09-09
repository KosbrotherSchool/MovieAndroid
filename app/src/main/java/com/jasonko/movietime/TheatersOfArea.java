package com.jasonko.movietime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        mAdapter.setOnItemClickListener(new TheatersListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Theater theater = mData.get(position);
                Intent intent1 = new Intent(TheatersOfArea.this, MovieListOfTheaterInAreaActivity.class);
                intent1.putExtra("theater_name", theater.getName());
                intent1.putExtra("theater_phone", theater.getPhone());
                intent1.putExtra("theater_address", theater.getAddress());
                intent1.putExtra("theater_id", theater.getTheater_id());



                //save the theater_id when any theater is viewed
                ////////////////////////////////////////////////
                SharedPreferences preferences = getSharedPreferences("RECENTLY_THEATERS", Context.MODE_PRIVATE);
                int count = preferences.getInt("COUNT", 0);
                int finalone = preferences.getInt("FINALONE", 1);
                int theater_id = theater.getTheater_id();

                if(count>0){
                    //check if the clicked theater is in the list already
                    Boolean notInList = true;
                    for(int i=1;i<=count; i++){
                        if(theater_id == preferences.getInt("THEATER"+i, 0)){
                            notInList = false;
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


                startActivity(intent1);

            }
        });


        mRecyclerView.setAdapter(mAdapter);


    }


}
