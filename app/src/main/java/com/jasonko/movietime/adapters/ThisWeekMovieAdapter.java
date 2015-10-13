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
        public TextView textClass;
        public TextView textEng;
        public ImageView imageMovie;
        TextView user_point;
        RatingBar ratingBar;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            imageMovie = (ImageView) mView.findViewById(R.id.movie_thisweek_image);
            textMovieTitle = (TextView) mView.findViewById(R.id.movie_thisweek_title_text);
            textType = (TextView) mView.findViewById(R.id.movie_thisweek_type_text);
            textClass = (TextView) mView.findViewById(R.id.movie_thisweek_class_text);
            textPublish = (TextView) mView.findViewById(R.id.movie_thisweek_publish_text);
            textEng = (TextView) mView.findViewById(R.id.movie_thisweek_eng_text);
            user_point = (TextView) mView.findViewById(R.id.text_user_point);
            ratingBar = (RatingBar) mView.findViewById(R.id.ratingBar);
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
        mImageLoader.DisplayImage(mMovies.get(position).getSmall_pic(), holder.imageMovie);
        holder.textMovieTitle.setText(mMovies.get(position).getTitle());
        holder.textType.setText(mMovies.get(position).getMovie_type());

        if (mMovies.get(position).getMovie_class().indexOf("限") != -1){
            holder.textClass.setBackgroundResource(R.drawable.class_red_selector);
            holder.textClass.setText("限");
            holder.textClass.setVisibility(View.VISIBLE);
        }else if(mMovies.get(position).getMovie_class().indexOf("保") != -1){
            holder.textClass.setBackgroundResource(R.drawable.class_blue_selector);
            holder.textClass.setText("保");
            holder.textClass.setVisibility(View.VISIBLE);
        }else if(mMovies.get(position).getMovie_class().indexOf("輔") != -1){
            holder.textClass.setBackgroundResource(R.drawable.class_yellow_selector);
            holder.textClass.setText("輔");
            holder.textClass.setVisibility(View.VISIBLE);
        }else if(mMovies.get(position).getMovie_class().indexOf("普") != -1){
            holder.textClass.setBackgroundResource(R.drawable.class_green_selector);
            holder.textClass.setText("普");
            holder.textClass.setVisibility(View.VISIBLE);
        }else {
            holder.textClass.setVisibility(View.GONE);
        }

        holder.textPublish.setText("上映日期：" + mMovies.get(position).getPublish_date());
        holder.textEng.setText(mMovies.get(position).getTitle_eng());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(mActivity, MovieActivity.class);
                newIntent.putExtra("movie_id", mMovies.get(position).getMovie_id());
                mActivity.startActivity(newIntent);
            }
        });

        if (mMovies.get(position).getPoints() == 0.0){
            holder.user_point.setText("尚未評分");
            holder.ratingBar.setVisibility(View.GONE);
        }else {
            holder.user_point.setText(Double.toString(mMovies.get(position).getPoints()) + "分");
            holder.ratingBar.setRating((float) (mMovies.get(position).getPoints() / 2));
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}