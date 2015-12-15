package com.jasonko.movietime.fragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonko.movietime.R;
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
 * Created by kolichung on 12/14/15.
 */
public class QuickTimeFragment extends Fragment {

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
    private LinearLayout linearButtons;
    private Button upButton;
    private Button downButton;

    public static QuickTimeFragment newInstance() {
        QuickTimeFragment fragment = new QuickTimeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_quick_time, container, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.my_progress_bar);
        noNetText = (TextView) view.findViewById(R.id.no_network_text);
        timeSpinner = (Spinner) view.findViewById(R.id.spinner1);
        areaSpinner = (Spinner) view.findViewById(R.id.spinner2);
        theaterSpinner = (Spinner) view.findViewById(R.id.spinner3);
        linearButtons = (LinearLayout) view.findViewById(R.id.linear_buttons);
        upButton = (Button) view.findViewById(R.id.quicktime_up_button);
        downButton = (Button) view.findViewById(R.id.quicktime_down_button);

        prefs = getActivity().getPreferences(0);

        List<String> times = Arrays.asList(getResources().getStringArray(R.array.times));
        ArrayAdapter<String> timesAdapter = new ArrayAdapter<>(getActivity(),
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
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(getActivity(),
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

                ArrayAdapter<String> theatersAdapter = new ArrayAdapter<>(getActivity(),
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


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_fragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);


        if (NetworkUtil.getConnectivityStatus(getActivity()) != 0) {
            if (!isTaskRunning) {
                new QuickTimesTask().execute();
            }
        }else{
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "無網路連線", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private class QuickTimesTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            noNetText.setVisibility(View.GONE);
            linearButtons.setVisibility(View.GONE);
            isTaskRunning = true;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            mMovieTimes = MovieTimeAPI.getMovieTimeByTime(searchTime, areaId, theaterId);
            if (mMovieTimes!=null && mMovieTimes.size()>0){
                Collections.sort(mMovieTimes);
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Object result) {
            isTaskRunning = false;
            if ((boolean) result){
                recyclerView.setVisibility(View.VISIBLE);
                quickTimeAdapter = new QuickTimeAdapter(mMovieTimes,QuickTimeFragment.this, searchTime);
                recyclerView.setAdapter(quickTimeAdapter);
            }else {
                noNetText.setVisibility(View.VISIBLE);
                linearButtons.setVisibility(View.VISIBLE);
                setUpTimeAndDownTimeButton();
            }
            mProgressBar.setVisibility(View.GONE);

        }
    }

    private void setUpTimeAndDownTimeButton() {
        int hour = Integer.parseInt(searchTime);
        int upHour = getUpHour(hour);
        int downHour = getDownHour(hour);
        if (upHour<10){
            upButton.setText("上一時刻 "+"0"+Integer.toString(upHour)+":00");
        }else {
            upButton.setText("上一時刻 "+Integer.toString(upHour)+":00");
        }
        if (downHour<10){
            downButton.setText("下一時刻 "+"0"+Integer.toString(downHour)+":00");
        }else {
            downButton.setText("下一時刻 "+Integer.toString(downHour)+":00");
        }

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upTimeSelection();
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downTimeSelection();
            }
        });
    }

    public void downTimeSelection(){
        int hour = Integer.parseInt(searchTime);
        if (hour==7){
            timeSpinner.setSelection(0);
        }else {
            timeSpinner.setSelection(timeSpinner.getSelectedItemPosition() + 1);
        }
    }

    public void upTimeSelection(){
        int hour = Integer.parseInt(searchTime);
        if (hour==8) {
            timeSpinner.setSelection(23);
        }else {
            timeSpinner.setSelection(timeSpinner.getSelectedItemPosition() - 1);
        }
    }

    private int getDownHour(int hour) {
        int downHour;
        if (hour== 0){
            downHour = 1;
        }else if(hour == 23){
            downHour = 0;
        }else {
            downHour = hour +1;
        }
        return downHour;
    }

    private int getUpHour(int hour) {
        int upHour;
        if (hour== 0){
            upHour = 23;
        }else if(hour == 23){
            upHour = 22;
        }else {
            upHour = hour -1;
        }
        return upHour;
    }

}
