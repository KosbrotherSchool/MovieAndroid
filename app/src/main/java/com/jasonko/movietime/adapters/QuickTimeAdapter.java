package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.MovieActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.model.MovieTime;
import com.jasonko.movietime.model.Theater;

import java.util.ArrayList;

/**
 * Created by kolichung on 10/8/15.
 */
public class QuickTimeAdapter extends RecyclerView.Adapter<QuickTimeAdapter.ViewHolder> {

    public ArrayList<MovieTime> mMovieTimes;
    public Activity mActivity;
    public String mHour;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView textTime;
        public TextView textTheater;
        public TextView textMovieTitle;
        public ImageView imageMap;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            textTime = (TextView) mView.findViewById(R.id.quick_time_text);
            textTheater = (TextView) mView.findViewById(R.id.quick_theater_text);
            textMovieTitle = (TextView) mView.findViewById(R.id.quick_movie_title_text);
            imageMap = (ImageView) mView.findViewById(R.id.quick_image_map);
        }

    }

    // clockHour = "10" or "11" etc
    public QuickTimeAdapter(ArrayList<MovieTime> movieTimes, Activity activity, String clockHour) {
        mMovieTimes = movieTimes;
        mActivity = activity;
        mHour = clockHour;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuickTimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quick_time, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Theater theTheater = Theater.getTheaterByID(mMovieTimes.get(position).getTheater_id());
        holder.textTheater.setText(theTheater.getName());
        holder.textMovieTitle.setText(mMovieTimes.get(position).getMovie_title());

        String timeString = mMovieTimes.get(position).getMovie_time();
        String theTime = timeString.substring(timeString.indexOf(mHour+"："), timeString.indexOf(mHour+"：")+5);
        holder.textTime.setText(theTime);

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
                Intent newIntent = new Intent(mActivity, MovieActivity.class);
                newIntent.putExtra("movie_id", mMovieTimes.get(position).getMovie_id());
                mActivity.startActivity(newIntent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovieTimes.size();
    }
}
