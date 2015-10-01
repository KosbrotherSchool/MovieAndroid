package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.MovieActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.Movie;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/28/15.
 */
public class ThisWeekMovieAdapter extends RecyclerView.Adapter<ThisWeekMovieAdapter.ViewHolder> {

    private ArrayList<Movie> mMovies;
    private ImageLoader mImageLoader;
    private Activity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView textMovieTitle;
        public TextView textType;
        public TextView textPublish;
        public TextView textActors;
        public ImageView imageMovie;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            imageMovie = (ImageView) mView.findViewById(R.id.movie_thisweek_image);
            textMovieTitle = (TextView) mView.findViewById(R.id.movie_thisweek_title_text);
            textType = (TextView) mView.findViewById(R.id.movie_thisweek_type_text);
            textActors = (TextView) mView.findViewById(R.id.movie_thisweek_actors_text);
            textPublish = (TextView) mView.findViewById(R.id.movie_thisweek_publish_text);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ThisWeekMovieAdapter(Activity mActivity, ArrayList<Movie> movies) {
        mMovies = movies;
        this.mActivity = mActivity;
        mImageLoader = new ImageLoader(this.mActivity,100);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ThisWeekMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_thisweek_horizontal, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mImageLoader.DisplayImage(mMovies.get(position).getSmall_pic(), holder.imageMovie);
        holder.textMovieTitle.setText(mMovies.get(position).getTitle());
        holder.textType.setText(mMovies.get(position).getMovie_type());
        holder.textActors.setText(mMovies.get(position).getActors());
        holder.textPublish.setText("上映日期：" + mMovies.get(position).getPublish_date() + "  " + mMovies.get(position).getMovie_class());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(mActivity, MovieActivity.class);
                newIntent.putExtra("movie_id", mMovies.get(position).getMovie_id());
                mActivity.startActivity(newIntent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}