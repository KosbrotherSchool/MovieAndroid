package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jasonko.movietime.AppParams;
import com.jasonko.movietime.NewsArticleActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.model.Blogger;

/**
 * Created by kolichung on 9/23/15.
 */
public class BloggerGridAdapter extends BaseAdapter {

    private Activity mActivity;
    private Blogger[] mBloggers;

    public BloggerGridAdapter(Activity activity, Blogger[] bloggers) {
        mActivity = activity;
        mBloggers = bloggers;
    }

    @Override
    public int getCount() {
        return mBloggers.length;
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_grid_ticket, null);
        }
        ImageView gridImage = (ImageView) convertView.findViewById(R.id.theater_grid_image);
        TextView gridTitle = (TextView) convertView.findViewById(R.id.theater_grid_title_text);

        gridImage.setImageResource(mBloggers[position].getBlogger_icon_id());
        gridTitle.setText(mBloggers[position].getBlogger_name());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppParams.isShowIntersitialAd(mActivity)){
                    requestNewInterstitial();
                }

                Intent newIntent = new Intent(mActivity, NewsArticleActivity.class);
                newIntent.putExtra("news_link", mBloggers[position].getBlogger_url());
                newIntent.putExtra("news_title", mBloggers[position].getBlogger_name());
                mActivity.startActivity(newIntent);

            }
        });

        return convertView;
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
