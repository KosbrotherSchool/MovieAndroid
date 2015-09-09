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
public class RankMovieExpectAdpter extends RecyclerView.Adapter<RankMovieExpectAdpter.ViewHolder> {

    private ArrayList<Movie> mMovies;
    private ImageLoader mImageLoader;
    private Activity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public ImageView imageMovie;
        public TextView textRankNum;
        public TextView textMovieTitle;
        public TextView textType;
        public TextView textExpect;
        public TextView textPublish;



        public ViewHolder(View v) {
            super(v);
            mView = v;
            imageMovie = (ImageView) mView.findViewById(R.id.movierank_expect_image);
            textRankNum = (TextView) mView.findViewById(R.id.movierank_expect_num_text);
            textMovieTitle = (TextView) mView.findViewById(R.id.movierank_expect_title_text);
            textType = (TextView) mView.findViewById(R.id.movierank_expect_type_text);
            textExpect = (TextView) mView.findViewById(R.id.movierank_expect_people_text);
            textPublish = (TextView) mView.findViewById(R.id.movierank_expect_publish_text);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RankMovieExpectAdpter(Activity mActivity, ArrayList<Movie> movies) {
        mMovies = movies;
        this.mActivity = mActivity;
        mImageLoader = new ImageLoader(this.mActivity);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RankMovieExpectAdpter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_rank_expect, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mImageLoader.DisplayImage(mMovies.get(position).getSmall_pic(), holder.imageMovie);
        holder.textRankNum.setText(Integer.toString(position + 1));
        holder.textMovieTitle.setText(mMovies.get(position).getTitle());
        holder.textType.setText(mMovies.get(position).getMovie_type());
        String expectPeople = Integer.toString(mMovies.get(position).getMovieRank().getExpect_people());
        String totalPeople = Integer.toString(mMovies.get(position).getMovieRank().getTotal_people());
        holder.textExpect.setText(expectPeople + " / " + totalPeople + " 人期待");
        holder.textPublish.setText(mMovies.get(position).getPublish_date() + "  " + mMovies.get(position).getMovie_class());

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
