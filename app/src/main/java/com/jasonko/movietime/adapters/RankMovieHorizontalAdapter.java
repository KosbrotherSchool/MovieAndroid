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
 * Created by kolichung on 8/27/15.
 */
public class RankMovieHorizontalAdapter extends RecyclerView.Adapter<RankMovieHorizontalAdapter.ViewHolder> {

    private ArrayList<Movie> mMovies;
    private ImageLoader mImageLoader;
    private Activity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView textRankNum;
        public TextView textMovieTitle;
        public TextView textActors;
        public TextView textPublish;
        public TextView textType;
        public ImageView imageMovie;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            textRankNum = (TextView) mView.findViewById(R.id.movierank_num_text);
            textMovieTitle = (TextView) mView.findViewById(R.id.movierank_title_text);
            imageMovie = (ImageView) mView.findViewById(R.id.movierank_image);
            textActors = (TextView) mView.findViewById(R.id.movierank_actors_text);
            textPublish = (TextView) mView.findViewById(R.id.movierank_publish_text);
            textType = (TextView) mView.findViewById(R.id.movierank_type_text);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RankMovieHorizontalAdapter(Activity mActivity, ArrayList<Movie> movies) {
        mMovies = movies;
        this.mActivity = mActivity;
        mImageLoader = new ImageLoader(this.mActivity, 100);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RankMovieHorizontalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_rank_horizontal, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        holder.textRankNum.setText(Integer.toString(position+1));
        holder.textMovieTitle.setText(mMovies.get(position).getTitle());
        holder.textActors.setText(mMovies.get(position).getActors());
        holder.textType.setText(mMovies.get(position).getMovie_type());
        holder.textPublish.setText(mMovies.get(position).getPublish_date() + "  " + mMovies.get(position).getMovie_class());
        mImageLoader.DisplayImage(mMovies.get(position).getSmall_pic(), holder.imageMovie);

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