package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class MovieGridAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<Movie> mMovies;
    private ImageLoader mImageLoader;

    public MovieGridAdapter(Activity activity, ArrayList<Movie> movies) {
        mActivity = activity;
        mMovies = movies;
        mImageLoader = new ImageLoader(this.mActivity, 100);
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // If convertView is null then inflate the appropriate layout file
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_grid_movie, null);
        }
        ImageView gridImage = (ImageView) convertView.findViewById(R.id.movie_grid_image);
        TextView gridTitle = (TextView) convertView.findViewById(R.id.movie_grid_title_text);
        TextView gridClass = (TextView) convertView.findViewById(R.id.movie_grid_class_text);
        TextView user_point = (TextView) convertView.findViewById(R.id.text_user_point);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

        LayerDrawable stars = (LayerDrawable) ratingBar
                .getProgressDrawable();
        stars.getDrawable(2).setColorFilter(mActivity.getResources().getColor(R.color.movie_indicator),
                PorterDuff.Mode.SRC_ATOP); // for filled stars
        stars.getDrawable(1).setColorFilter(mActivity.getResources().getColor(R.color.movie_indicator),
                PorterDuff.Mode.SRC_ATOP); // for half filled stars
        stars.getDrawable(0).setColorFilter(mActivity.getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);

        mImageLoader.DisplayImage(mMovies.get(position).getSmall_pic(), gridImage);
        gridTitle.setText(mMovies.get(position).getTitle());

        if (mMovies.get(position).getMovie_class().indexOf("限") != -1){
            gridClass.setBackgroundResource(R.drawable.class_red_selector);
            gridClass.setText("限");
            gridClass.setVisibility(View.VISIBLE);
        }else if(mMovies.get(position).getMovie_class().indexOf("保") != -1){
            gridClass.setBackgroundResource(R.drawable.class_blue_selector);
            gridClass.setText("保");
            gridClass.setVisibility(View.VISIBLE);
        }else if(mMovies.get(position).getMovie_class().indexOf("輔") != -1){
            gridClass.setBackgroundResource(R.drawable.class_yellow_selector);
            gridClass.setText("輔");
            gridClass.setVisibility(View.VISIBLE);
        }else if(mMovies.get(position).getMovie_class().indexOf("普") != -1){
            gridClass.setBackgroundResource(R.drawable.class_green_selector);
            gridClass.setText("普");
            gridClass.setVisibility(View.VISIBLE);
        }else {
            gridClass.setVisibility(View.GONE);
        }

        if (mMovies.get(position).getPoints() == 0.0){
            user_point.setText("尚未評分");
            ratingBar.setVisibility(View.GONE);
        }else {
            user_point.setText(Double.toString(mMovies.get(position).getPoints()) + "分");
            ratingBar.setRating((float) (mMovies.get(position).getPoints() / 2));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(mActivity, MovieActivity.class);
                newIntent.putExtra("movie_id", mMovies.get(position).getMovie_id());
                mActivity.startActivity(newIntent);
            }
        });

        return convertView;
    }
}
