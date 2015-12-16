package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.WebViewArticleActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.BlogPost;

import java.util.ArrayList;

/**
 * Created by kolichung on 10/14/15.
 */
public class BlogPostsAdapter extends RecyclerView.Adapter<BlogPostsAdapter.ViewHolder> {



    public ArrayList<BlogPost> mPosts;
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
    public BlogPostsAdapter(Activity activity, ArrayList<BlogPost> posts) {
        mPosts = posts;
        this.mActivity = activity;
        mImageLoader = new ImageLoader(this.mActivity);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BlogPostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
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
        holder.textTilte.setText(mPosts.get(position).getTitle());
        holder.textDate.setText(mPosts.get(position).getPub_date());
        mImageLoader.DisplayImage(mPosts.get(position).getPic_link(), holder.imageNews);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(mActivity, WebViewArticleActivity.class);
                newIntent.putExtra("news_link", mPosts.get(position).getLink());
                newIntent.putExtra("news_title", mPosts.get(position).getTitle());
                mActivity.startActivity(newIntent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPosts.size();
    }
}
