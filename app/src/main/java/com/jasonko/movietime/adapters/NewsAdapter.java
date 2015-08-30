package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.NewsArticleActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.MovieNews;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/26/15.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {



    public ArrayList<MovieNews> mNewses;
    public Activity mActivity;
    public ImageLoader mImageLoader;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView textTilte;
        public TextView textDate;
        public ImageView imageNews;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            textTilte = (TextView) mView.findViewById(R.id.news_title);
            textDate = (TextView) mView.findViewById(R.id.news_date);
            imageNews = (ImageView) mView.findViewById(R.id.news_image);
        }

    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdapter(Activity activity, ArrayList<MovieNews> newses) {
        mNewses = newses;
        this.mActivity = activity;
        mImageLoader = new ImageLoader(this.mActivity);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textTilte.setText(mNewses.get(position).getTitle());
        holder.textDate.setText(mNewses.get(position).getPublish_day());
        mImageLoader.DisplayImage(mNewses.get(position).getPic_link(), holder.imageNews);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(mActivity, NewsArticleActivity.class);
                newIntent.putExtra("news_link", mNewses.get(position).getNews_link());
                mActivity.startActivity(newIntent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mNewses.size();
    }
}