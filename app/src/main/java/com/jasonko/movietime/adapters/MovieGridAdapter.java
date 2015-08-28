package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        mImageLoader = new ImageLoader(this.mActivity);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // If convertView is null then inflate the appropriate layout file
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_grid_movie, null);
        }
        ImageView gridImage = (ImageView) convertView.findViewById(R.id.movie_grid_image);
        TextView gridTitle = (TextView) convertView.findViewById(R.id.movie_grid_title_text);
        TextView gridClass = (TextView) convertView.findViewById(R.id.movie_grid_class_text);

        mImageLoader.DisplayImage(mMovies.get(position).getSmall_pic(), gridImage);
        gridTitle.setText(mMovies.get(position).getTitle());
        gridClass.setText(mMovies.get(position).getMovie_class());

        return convertView;
    }
}
