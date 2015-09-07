package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.MovieActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.dao.FollowMovie;
import com.jasonko.movietime.imageloader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by kolichung on 9/6/15.
 */
public class FollowMovieGridAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<FollowMovie> mMovies;
    private ImageLoader mImageLoader;

    public FollowMovieGridAdapter(Activity activity, ArrayList<FollowMovie> movies) {
        mActivity = activity;
        mMovies = movies;
        mImageLoader = new ImageLoader(this.mActivity, 80);
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_grid_movie_follow, null);
        }
        ImageView gridImage = (ImageView) convertView.findViewById(R.id.movie_grid_image);
        TextView gridTitle = (TextView) convertView.findViewById(R.id.movie_grid_title_text);
        TextView gridClass = (TextView) convertView.findViewById(R.id.movie_grid_class_text);
        TextView gridPublish = (TextView) convertView.findViewById(R.id.movie_grid_publish_text);

        mImageLoader.DisplayImage(mMovies.get(position).getSmall_pic(), gridImage);
        gridTitle.setText(mMovies.get(position).getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String datString = sdf.format(mMovies.get(position).getPublish_day());
        gridPublish.setText(datString);

        gridClass.setText(mMovies.get(position).getMovie_class());

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