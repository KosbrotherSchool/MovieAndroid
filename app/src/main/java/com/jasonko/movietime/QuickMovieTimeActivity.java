package com.jasonko.movietime;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonko.movietime.adapters.QuickTimeAdapter;
import com.jasonko.movietime.api.MovieTimeAPI;
import com.jasonko.movietime.model.Area;
import com.jasonko.movietime.model.MovieTime;
import com.jasonko.movietime.model.Theater;
import com.jasonko.movietime.tool.NetworkUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by kolichung on 10/8/15.
 */
public class QuickMovieTimeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MovieTime> mMovieTimes = new ArrayList<>();
    private QuickTimeAdapter quickTimeAdapter;

    private ProgressBar mProgressBar;
    private TextView noNetText;

    private Spinner timeSpinner;
    private Spinner areaSpinner;
    private Spinner theaterSpinner;

    private ArrayList<String> areaNames = new ArrayList<>();
    private ArrayList<Integer> areaIDs = new ArrayList<>();

    private SharedPreferences prefs;
    private String searchTime;
    private int areaId;
    private int theaterId;

    private ArrayList<Theater> currentAreaTheaters;

//    int taskRunTimes = 0;
    boolean isTaskRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_time);
        mProgressBar = (ProgressBar) findViewById(R.id.my_progress_bar);
        noNetText = (TextView) findViewById(R.id.no_network_text);
        timeSpinner = (Spinner) findViewById(R.id.spinner1);
        areaSpinner = (Spinner) findViewById(R.id.spinner2);
        theaterSpinner = (Spinner) findViewById(R.id.spinner3);

        prefs = getPreferences(0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("依時刻快查");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        List<String> times = Arrays.asList(getResources().getStringArray(R.array.times));
        ArrayAdapter<String> timesAdapter = new ArrayAdapter<>(this,
                R.layout.myspinner, times);
        timesAdapter.setDropDownViewResource(R.layout.myspinner);
        timeSpinner.setAdapter(timesAdapter);

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int spinnerPosition;
        if (hour >= 10){
            spinnerPosition = timesAdapter.getPosition(Integer.toString(hour) + ":00");
            searchTime = Integer.toString(hour);
        }else {
            spinnerPosition = timesAdapter.getPosition("0"+Integer.toString(hour) + ":00");
            searchTime = "0"+Integer.toString(hour);
        }
        timeSpinner.setSelection(spinnerPosition);

        theaterId = prefs.getInt("selected theater id", 0);

        ArrayList<Area> areas = Area.getAreas();
        for (int i= 0; i<areas.size(); i++){
            areaNames.add(areas.get(i).getName());
            areaIDs.add(areas.get(i).getArea_id());
        }
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(this,
                R.layout.myspinner, areaNames);
        areaSpinner.setAdapter(areaAdapter);

        int areaPosition = prefs.getInt("area position",0);
        areaSpinner.setSelection(areaPosition);
        areaId = areaIDs.get(areaPosition);

        timeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {

                String timeStr = timeSpinner.getSelectedItem().toString();
                searchTime = timeStr.substring(0, 2);

                int currentAreaPosition = areaSpinner.getSelectedItemPosition();
                areaId = areaIDs.get(currentAreaPosition);

                if (!isTaskRunning) {
                    new QuickTimesTask().execute();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        areaSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){

                String timeStr = timeSpinner.getSelectedItem().toString();
                searchTime = timeStr.substring(0, 2);

                int currentAreaPosition = areaSpinner.getSelectedItemPosition();
                prefs.edit().putInt("area position", currentAreaPosition).commit();
                areaId = areaIDs.get(currentAreaPosition);

                // Set Theater Spinner
                ArrayList<String> theatersNameArray = new ArrayList<>();
                theatersNameArray.add("全部戲院");

                // get area theaters
                currentAreaTheaters = Theater.getTheaters(areaId);

                int theaterPosition = 0;

                // get only theater name, and add to theatersNameArray
                for (int i = 0; i < currentAreaTheaters.size(); i++) {
                    theatersNameArray.add(currentAreaTheaters.get(i).getName());
                    if (theaterId == currentAreaTheaters.get(i).getTheater_id()){
                        theaterPosition = i + 1;
                    }
                }

                ArrayAdapter<String> theatersAdapter = new ArrayAdapter<>(QuickMovieTimeActivity.this,
                        R.layout.myspinner, theatersNameArray);

                theaterSpinner.setAdapter(theatersAdapter);
                theaterSpinner.setSelection(theaterPosition);

                if (theaterPosition == 0) {
                    theaterId = 0;
                }

                if (!isTaskRunning) {
                    new QuickTimesTask().execute();
                }

            }
            public void onNothingSelected(AdapterView arg0) {

            }
        });

        theaterSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                String timeStr = timeSpinner.getSelectedItem().toString();
                searchTime = timeStr.substring(0, 2);

                int currentAreaPosition = areaSpinner.getSelectedItemPosition();
                areaId = areaIDs.get(currentAreaPosition);

                if (position==0){
                    theaterId = 0;
                }else {
                    theaterId = currentAreaTheaters.get(position - 1).getTheater_id();
                }
                prefs.edit().putInt("selected theater id", theaterId).commit();

                if (!isTaskRunning) {
                    new QuickTimesTask().execute();
                }
            }
            public void onNothingSelected(AdapterView arg0) {

            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_fragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);


        if (NetworkUtil.getConnectivityStatus(QuickMovieTimeActivity.this) != 0) {
            if (!isTaskRunning) {
                new QuickTimesTask().execute();
            }
        }else{
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(QuickMovieTimeActivity.this, "無網路連線", Toast.LENGTH_SHORT).show();
        }

    }

    private class QuickTimesTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            noNetText.setVisibility(View.GONE);
//            Toast.makeText(QuickMovieTimeActivity.this, Integer.toString(taskRunTimes),Toast.LENGTH_SHORT).show();
//            taskRunTimes ++;
            isTaskRunning = true;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            mMovieTimes = MovieTimeAPI.getMovieTimeByTime(searchTime,areaId,theaterId);
            Collections.sort(mMovieTimes);
            if (mMovieTimes!=null && mMovieTimes.size()>0){
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Object result) {
            isTaskRunning = false;
            if ((boolean) result){
                recyclerView.setVisibility(View.VISIBLE);
                quickTimeAdapter = new QuickTimeAdapter(mMovieTimes,QuickMovieTimeActivity.this, searchTime);
                recyclerView.setAdapter(quickTimeAdapter);
            }else {
                noNetText.setVisibility(View.VISIBLE);
            }
            mProgressBar.setVisibility(View.GONE);

        }
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