package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.TheaterActivity;
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
//        public TextView textTeaterAddress;
        public ExpandableHeightGridView gridTheaterMovie;
        public ImageView imageMap;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            textTheaterTitle = (TextView) mView.findViewById(R.id.movietime_theater_title);
            textRemark = (TextView) mView.findViewById(R.id.movietime_remark);
//            textTeaterAddress = (TextView) mView.findViewById(R.id.movietime_theater_address);
            gridTheaterMovie = (ExpandableHeightGridView) mView.findViewById(R.id.movietime_theater_movietime_grid);
            imageMap = (ImageView) mView.findViewById(R.id.image_map);
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
        if (theTheater != null) {
            holder.textTheaterTitle.setText(theTheater.getName());
            holder.textRemark.setText(mMovieTimes.get(position).getRemark());
            String[] mStrings = mMovieTimes.get(position).getMovie_time().split(",");
            ArrayAdapter arrayAdapter = new ArrayAdapter(mActivity, R.layout.mytext, mStrings);
            holder.gridTheaterMovie.setExpanded(true);
            holder.gridTheaterMovie.setAdapter(arrayAdapter);
            holder.imageMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Theater theTheater = Theater.getTheaterByID(mMovieTimes.get(position).getTheater_id());
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("geo:0,0?q=" + theTheater.getAddress()));
                    mActivity.startActivity(intent);
                }
            });
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent theaterIntent = new Intent(mActivity, TheaterActivity.class);
                    theaterIntent.putExtra("theater_id", mMovieTimes.get(position).getTheater_id());
                    mActivity.startActivity(theaterIntent);
                }
            });
        }else {
            holder.textTheaterTitle.setText("請更新至最新版以增加戲院資料");
            holder.textRemark.setText("");
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovieTimes.size();
    }
}
