package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
public class RankMovieWeekAdapter extends RecyclerView.Adapter<RankMovieWeekAdapter.ViewHolder> {

    private ArrayList<Movie> mMovies;
    private ImageLoader mImageLoader;
    private Activity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView textRankNum;
        public TextView textMovieTitle;
        public TextView textDuration;
        public TextView textPublish;
        public TextView textActors;
        public ImageView imageMovie;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            imageMovie = (ImageView) mView.findViewById(R.id.movierank_week_image);
            textRankNum = (TextView) mView.findViewById(R.id.movierank_week_num_text);
            textDuration = (TextView) mView.findViewById(R.id.movierank_week_duration_text);
            textMovieTitle = (TextView) mView.findViewById(R.id.movierank_week_title_text);
            textActors = (TextView) mView.findViewById(R.id.movierank_week_actors_text);
            textPublish = (TextView) mView.findViewById(R.id.movierank_week_publish_text);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RankMovieWeekAdapter(Activity mActivity, ArrayList<Movie> movies) {
        mMovies = movies;
        this.mActivity = mActivity;
        mImageLoader = new ImageLoader(this.mActivity,100);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RankMovieWeekAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_rank_week, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        holder.textRankNum.setText("第"+ Integer.toString(mMovies.get(position).getMovieRank().getThe_week())+"週");
        holder.textDuration.setText(Html.fromHtml(mMovies.get(position).getMovieRank().getStatic_duration()));
        holder.textMovieTitle.setText(mMovies.get(position).getTitle());
        holder.textActors.setText(mMovies.get(position).getActors());
        holder.textPublish.setText("上映週數：" + Integer.toString(mMovies.get(position).getMovieRank().getPublish_weeks()) + "  " + mMovies.get(position).getMovie_class());
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