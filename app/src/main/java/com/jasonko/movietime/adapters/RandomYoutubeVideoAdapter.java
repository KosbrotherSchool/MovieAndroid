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
import com.jasonko.movietime.model.MyYoutubeVideo;

import java.util.ArrayList;

/**
 * Created by kolichung on 8/25/15.
 */
public class RandomYoutubeVideoAdapter extends RecyclerView.Adapter<RandomYoutubeVideoAdapter.ViewHolder> {

private ArrayList<MyYoutubeVideo> mVideos;
private ImageLoader mImageLoader;
private Activity mActivity;

public static class ViewHolder extends RecyclerView.ViewHolder {

    public View mView;
    public TextView textVideoTitle;
    public ImageView imageVideo;

    public ViewHolder(View v) {
        super(v);
        mView = v;
        textVideoTitle = (TextView) mView.findViewById(R.id.text_random_video_tile);
        imageVideo = (ImageView) mView.findViewById(R.id.image_random_video);
    }

}

    // Provide a suitable constructor (depends on the kind of dataset)
    public RandomYoutubeVideoAdapter(Activity mActivity, ArrayList<MyYoutubeVideo> videos) {
        mVideos = videos;
        this.mActivity = mActivity;
        mImageLoader = new ImageLoader(this.mActivity);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RandomYoutubeVideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feature_youtube, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textVideoTitle.setText(mVideos.get(position).getTitle());
        mImageLoader.DisplayImage("https://i.ytimg.com/vi/"+ mVideos.get(position).getYoutubeID()+"/mqdefault.jpg", holder.imageVideo);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mVideos.size();
    }
}
