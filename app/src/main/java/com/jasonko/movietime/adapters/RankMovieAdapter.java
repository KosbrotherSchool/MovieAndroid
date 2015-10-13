package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jasonko.movietime.MovieActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.Movie;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/25/15.
 */
public class RankMovieAdapter extends RecyclerView.Adapter<RankMovieAdapter.ViewHolder> {

    private ArrayList<Movie> mMovies;
    private ImageLoader mImageLoader;
    private Activity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView textRankNum;
        public TextView textMovieTitle;
        public ImageView imageMovie;
        TextView user_point;
        RatingBar ratingBar;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            textRankNum = (TextView) mView.findViewById(R.id.text_rank_num);
            textMovieTitle = (TextView) mView.findViewById(R.id.text_rank_movie_tile);
            imageMovie = (ImageView) mView.findViewById(R.id.image_rank);
            user_point = (TextView) mView.findViewById(R.id.text_user_point);
            ratingBar = (RatingBar) mView.findViewById(R.id.ratingBar);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RankMovieAdapter(Activity mActivity, ArrayList<Movie> movies) {
        mMovies = movies;
        this.mActivity = mActivity;
        mImageLoader = new ImageLoader(this.mActivity, 100);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RankMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_rank, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(v);

        LayerDrawable stars = (LayerDrawable) viewHolder.ratingBar
                .getProgressDrawable();
        stars.getDrawable(2).setColorFilter(mActivity.getResources().getColor(R.color.movie_indicator),
                PorterDuff.Mode.SRC_ATOP); // for filled stars
        stars.getDrawable(1).setColorFilter(mActivity.getResources().getColor(R.color.movie_indicator),
                PorterDuff.Mode.SRC_ATOP); // for half filled stars
        stars.getDrawable(0).setColorFilter(mActivity.getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textRankNum.setText(Integer.toString(position+1));
        holder.textMovieTitle.setText(mMovies.get(position).getTitle());
        mImageLoader.DisplayImage(mMovies.get(position).getSmall_pic(), holder.imageMovie);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(mActivity, MovieActivity.class);
                newIntent.putExtra("movie_id", mMovies.get(position).getMovie_id());
                mActivity.startActivity(newIntent);
            }
        });

        holder.user_point.setText(Double.toString(mMovies.get(position).getPoints()) + "分");
        holder.ratingBar.setRating((float) (mMovies.get(position).getPoints() / 2));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
