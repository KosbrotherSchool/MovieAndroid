package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jasonko.movietime.R;

/**
 * Created by kolichung on 12/16/15.
 */
public class MessageTagAdapter extends RecyclerView.Adapter<MessageTagAdapter.ViewHolder> {

    private String tags[];
    private Activity mActivity;
    private int currentPosition = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView textArea;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            textArea = (TextView) mView.findViewById(R.id.movietime_area_text);
        }

    }

    public MessageTagAdapter(String tags[], Activity activity) {
        this.tags = tags;
        mActivity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MessageTagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
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
        holder.textArea.setText(tags[position]);
        if (position == currentPosition){
            holder.textArea.setBackgroundResource(R.color.movie_indicator);
            holder.textArea.setTextColor(mActivity.getResources().getColor(R.color.white));
        }else{
            holder.textArea.setBackgroundResource(R.color.white);
            holder.textArea.setTextColor(mActivity.getResources().getColor(R.color.movie_indicator));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = position;
                MessageTagAdapter.this.notifyDataSetChanged();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tags.length;
    }

    public String getTagString(){
        return tags[currentPosition];
    }
}