package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.model.Review;

import java.util.ArrayList;

/**
 * Created by kolichung on 10/2/15.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    Activity mActivity;
    LayoutInflater mLayoutInflater;
    ArrayList<Review> mReviews;
    View mView;

    public CommentListAdapter(Activity activity, ArrayList<Review> reviews) {
        mActivity = activity;
        mLayoutInflater = LayoutInflater.from(activity);
        mReviews = reviews;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
            mView = arg0;
        }

        TextView userId;
        TextView user_point;
        TextView user_comment;
        TextView publish_date;
        RatingBar ratingBar;
        ImageView imageView;
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public CommentListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_user_comment, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.userId = (TextView) view.findViewById(R.id.text_userId);
        viewHolder.user_point = (TextView) view.findViewById(R.id.text_user_point);
        viewHolder.user_comment = (TextView) view.findViewById(R.id.text_user_comment);
        viewHolder.publish_date = (TextView) view.findViewById(R.id.text_publish_date);
        viewHolder.ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        viewHolder.imageView = (ImageView) view.findViewById(R.id.comment_image);

        LayerDrawable stars = (LayerDrawable) viewHolder.ratingBar
                .getProgressDrawable();
        stars.getDrawable(2).setColorFilter(mActivity.getResources().getColor(R.color.movie_indicator),
                PorterDuff.Mode.SRC_ATOP); // for filled stars
        stars.getDrawable(1).setColorFilter(mActivity.getResources().getColor(R.color.movie_indicator),
                PorterDuff.Mode.SRC_ATOP); // for half filled stars
        stars.getDrawable(0).setColorFilter(mActivity.getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Review review = mReviews.get(i);
        viewHolder.userId.setText(review.getAuthor());
        viewHolder.user_point.setText(Double.toString(review.getPoint())+"分");
        viewHolder.user_comment.setText(review.getContent());
        viewHolder.publish_date.setText(review.getPublish_date());
        viewHolder.ratingBar.setRating((float) (review.getPoint() / 2));
        switch (review.getHead_index()){
            case 1:
                viewHolder.imageView.setImageResource(R.drawable.head_captain);
                break;
            case 2:
                viewHolder.imageView.setImageResource(R.drawable.head_iron_man);
                break;
            case 3:
                viewHolder.imageView.setImageResource(R.drawable.head_black_widow);
                break;
            case 4:
                viewHolder.imageView.setImageResource(R.drawable.head_thor);
                break;
            case 5:
                viewHolder.imageView.setImageResource(R.drawable.head_hulk);
                break;
            case 6:
                viewHolder.imageView.setImageResource(R.drawable.head_hawkeye);
                break;
        }
    }

}
