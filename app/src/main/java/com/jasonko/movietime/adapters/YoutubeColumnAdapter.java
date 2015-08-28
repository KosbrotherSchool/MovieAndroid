package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.RecommendColumnVideosActivity;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.MyYoutubeColumn;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/28/15.
 */
public class YoutubeColumnAdapter extends RecyclerView.Adapter<YoutubeColumnAdapter.ViewHolder> {



    public ArrayList<MyYoutubeColumn> myYoutubeColumns;
    public Activity mActivity;
    public ImageLoader mImageLoader;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView textTilte;
        public ImageView imageNews;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            textTilte = (TextView) mView.findViewById(R.id.column_title);
            imageNews = (ImageView) mView.findViewById(R.id.column_image);
        }

    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public YoutubeColumnAdapter(Activity activity, ArrayList<MyYoutubeColumn> columns) {
        myYoutubeColumns = columns;
        this.mActivity = activity;
        mImageLoader = new ImageLoader(activity, 300);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public YoutubeColumnAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_column, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textTilte.setText(myYoutubeColumns.get(position).getName());
        mImageLoader.DisplayImage(myYoutubeColumns.get(position).getImage_link(), holder.imageNews);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(mActivity, RecommendColumnVideosActivity.class);
                newIntent.putExtra("column_id", myYoutubeColumns.get(position).getColumn_id());
                newIntent.putExtra("image_link", myYoutubeColumns.get(position).getImage_link());
                mActivity.startActivity(newIntent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myYoutubeColumns.size();
    }
}