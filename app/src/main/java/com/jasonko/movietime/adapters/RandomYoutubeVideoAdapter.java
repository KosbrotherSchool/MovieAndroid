package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jasonko.movietime.AppParams;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textVideoTitle.setText(mVideos.get(position).getTitle());
        mImageLoader.DisplayImage(mVideos.get(position).getPicLink(), holder.imageVideo);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppParams.isShowIntersitialAd(mActivity)){
                    requestNewInterstitial();
                }
                String url = mVideos.get(position).getYoutbeLink();
                Intent intentGood = new Intent(Intent.ACTION_VIEW);
                intentGood.setData(Uri.parse(url));
                mActivity.startActivity(intentGood);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    private InterstitialAd mInterstitialAd;
    private void requestNewInterstitial() {
        mInterstitialAd = new InterstitialAd(mActivity);
        mInterstitialAd.setAdUnitId(mActivity.getResources().getString(R.string.intersitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }
        });
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("9A6CCAB163B87B4531D8D6278B898D2C")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
}
