package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.model.MovieTime;
import com.jasonko.movietime.model.Theater;
import com.jasonko.movietime.tool.ExpandableHeightGridView;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/27/15.
 */
public class MovieTimeAdapter extends RecyclerView.Adapter<MovieTimeAdapter.ViewHolder> {

    public ArrayList<MovieTime> mMovieTimes;
    public Activity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView textTheaterTitle;
        public TextView textRemark;
        public TextView textTeaterAddress;
        public ExpandableHeightGridView gridTheaterMovie;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            textTheaterTitle = (TextView) mView.findViewById(R.id.movietime_theater_title);
            textRemark = (TextView) mView.findViewById(R.id.movietime_remark);
            textTeaterAddress = (TextView) mView.findViewById(R.id.movietime_theater_address);
            gridTheaterMovie = (ExpandableHeightGridView) mView.findViewById(R.id.movietime_theater_movietime_grid);
        }

    }

    public MovieTimeAdapter(ArrayList<MovieTime> movieTimes, Activity activity) {
        mMovieTimes = movieTimes;
        mActivity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieTimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_time, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Theater theTheater = Theater.getTheaterByID(mMovieTimes.get(position).getTheater_id());
        holder.textTheaterTitle.setText(theTheater.getName());
        holder.textTeaterAddress.setText(theTheater.getAddress());
        holder.textRemark.setText(mMovieTimes.get(position).getRemark());
        String[] mStrings = mMovieTimes.get(position).getMovie_time().split(",");
        ArrayAdapter arrayAdapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, mStrings);
        holder.gridTheaterMovie.setExpanded(true);
        holder.gridTheaterMovie.setAdapter(arrayAdapter);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovieTimes.size();
    }
}
