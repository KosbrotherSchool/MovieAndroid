package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.Movie;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/28/15.
 */
public class RankMovieSatisfyAdapter extends RecyclerView.Adapter<RankMovieSatisfyAdapter.ViewHolder> {

    private ArrayList<Movie> mMovies;
    private ImageLoader mImageLoader;
    private Activity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public ImageView imageMovie;
        public TextView textRankNum;
        public TextView textMovieTitle;
        public TextView textType;
        public TextView textSatisfy;
        public TextView textPublish;



        public ViewHolder(View v) {
            super(v);
            mView = v;
            imageMovie = (ImageView) mView.findViewById(R.id.movierank_satisfy_image);
            textRankNum = (TextView) mView.findViewById(R.id.movierank_satisfy_num_text);
            textMovieTitle = (TextView) mView.findViewById(R.id.movierank_satisfy_title_text);
            textType = (TextView) mView.findViewById(R.id.movierank_satisfy_type_text);
            textSatisfy = (TextView) mView.findViewById(R.id.movierank_satisfy_people_text);
            textPublish = (TextView) mView.findViewById(R.id.movierank_satisfy_publish_text);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RankMovieSatisfyAdapter(Activity mActivity, ArrayList<Movie> movies) {
        mMovies = movies;
        this.mActivity = mActivity;
        mImageLoader = new ImageLoader(this.mActivity);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RankMovieSatisfyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_rank_satisfied, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mImageLoader.DisplayImage(mMovies.get(position).getSmall_pic(), holder.imageMovie);
        holder.textRankNum.setText(Integer.toString(position + 1));
        holder.textMovieTitle.setText(mMovies.get(position).getTitle());
        holder.textType.setText(mMovies.get(position).getMovie_type());
        String totalPeople = Integer.toString(mMovies.get(position).getMovieRank().getTotal_people());
        holder.textSatisfy.setText( "滿意度： " + mMovies.get(position).getMovieRank().getSatisfied_num() + "/ 5  共" + totalPeople +"人投票");
        holder.textPublish.setText(mMovies.get(position).getPublish_date() + "  " + mMovies.get(position).getMovie_class());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
