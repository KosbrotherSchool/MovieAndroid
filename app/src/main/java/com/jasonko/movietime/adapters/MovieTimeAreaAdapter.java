package com.jasonko.movietime.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.fragments.MovieTimeFragment;
import com.jasonko.movietime.model.Area;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/27/15.
 */
public class MovieTimeAreaAdapter extends RecyclerView.Adapter<MovieTimeAreaAdapter.ViewHolder> {

    public ArrayList<Area> mAreas;
    public int mArea_id;
    public MovieTimeFragment mFragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView textArea;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            textArea = (TextView) mView.findViewById(R.id.movietime_area_text);
        }

    }

    public MovieTimeAreaAdapter(ArrayList<Area> areas,int area_id, MovieTimeFragment movieTimeFragment) {
        mAreas = areas;
        mArea_id = area_id;
        mFragment = movieTimeFragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieTimeAreaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_time_area, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textArea.setText(mAreas.get(position).getName());
        if (mAreas.get(position).getArea_id() == mArea_id){
            holder.textArea.setBackgroundResource(R.drawable.movietime_area_style);
            holder.textArea.setTextColor(mFragment.getResources().getColor(R.color.white));
        }else{
            holder.textArea.setBackgroundResource(R.color.white);
            holder.textArea.setTextColor(mFragment.getResources().getColor(R.color.movie_indicator));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArea_id = mAreas.get(position).getArea_id();
                MovieTimeAreaAdapter.this.notifyDataSetChanged();
                mFragment.runGetMovieTimesTask(mArea_id);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mAreas.size();
    }
}