package com.jasonko.movietime.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jasonko.movietime.MovieActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.fragments.QuickTimeFragment;
import com.jasonko.movietime.model.MovieTime;
import com.jasonko.movietime.model.Theater;

import java.util.ArrayList;

/**
 * Created by kolichung on 10/8/15.
 */
public class QuickTimeAdapter extends RecyclerView.Adapter<QuickTimeAdapter.ViewHolder> {

    public ArrayList<MovieTime> mMovieTimes;
    public QuickTimeFragment mFragment;
    public String mHour;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView textTime;
        public TextView textTheater;
        public TextView textMovieTitle;
        public ImageView imageMap;
        public LinearLayout linearButtons;
        public CardView cardView;
        public Button upButton;
        public Button downButton;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            textTime = (TextView) mView.findViewById(R.id.quick_time_text);
            textTheater = (TextView) mView.findViewById(R.id.quick_theater_text);
            textMovieTitle = (TextView) mView.findViewById(R.id.quick_movie_title_text);
            imageMap = (ImageView) mView.findViewById(R.id.quick_image_map);
            linearButtons = (LinearLayout) mView.findViewById(R.id.linear_buttons);
            cardView = (CardView) mView.findViewById(R.id.cardview);
            upButton = (Button) mView.findViewById(R.id.quicktime_up_button);
            downButton = (Button) mView.findViewById(R.id.quicktime_down_button);
        }

    }

    // clockHour = "10" or "11" etc
    public QuickTimeAdapter(ArrayList<MovieTime> movieTimes, QuickTimeFragment fragment, String clockHour) {
        mMovieTimes = movieTimes;
        mFragment = fragment;
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
        if (theTheater!=null) {
            holder.textTheater.setText(theTheater.getName());
            holder.imageMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Theater theTheater = Theater.getTheaterByID(mMovieTimes.get(position).getTheater_id());
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("geo:0,0?q=" + theTheater.getAddress()));
                    mFragment.getActivity().startActivity(intent);
                }
            });
        }else {
            holder.textTheater.setText("請更新至最新版以增加戲院資料");
        }

        holder.textMovieTitle.setText(mMovieTimes.get(position).getMovie_title());
        String timeString = mMovieTimes.get(position).getMovie_time();
        String theTime = timeString.substring(timeString.indexOf(mHour+"："), timeString.indexOf(mHour+"：")+5);
        holder.textTime.setText(theTime);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(mFragment.getActivity(), MovieActivity.class);
                newIntent.putExtra("movie_id", mMovieTimes.get(position).getMovie_id());
                mFragment.getActivity().startActivity(newIntent);
            }
        });

        if (position == mMovieTimes.size()-1){
            int hour = Integer.parseInt(mHour);
            int upHour;
            int downHour;
            if (hour== 0){
                upHour = 23;
                downHour = 1;
            }else if(hour == 23){
                upHour = 22;
                downHour = 0;
            }else {
                upHour = hour -1;
                downHour = hour +1;
            }
            if (upHour<10){
                holder.upButton.setText("上一時刻 "+"0"+Integer.toString(upHour)+":00");
            }else {
                holder.upButton.setText("上一時刻 "+Integer.toString(upHour)+":00");
            }
            if (downHour<10){
                holder.downButton.setText("下一時刻 "+"0"+Integer.toString(downHour)+":00");
            }else {
                holder.downButton.setText("下一時刻 "+Integer.toString(downHour)+":00");
            }
            holder.linearButtons.setVisibility(View.VISIBLE);

            holder.downButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragment.downTimeSelection();
                }
            });

            holder.upButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragment.upTimeSelection();
                }
            });

        }else {
            holder.linearButtons.setVisibility(View.GONE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovieTimes.size();
    }
}
