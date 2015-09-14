package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.jasonko.movietime.R;
import com.jasonko.movietime.model.OrderTheater;

/**
 * Created by kolichung on 8/31/15.
 */
public class TheaterGridAdapter extends BaseAdapter {

    private Activity mActivity;
    private OrderTheater[] mOrderTheaters;

    public TheaterGridAdapter(Activity activity, OrderTheater[] orderTheaters) {
        mActivity = activity;
        mOrderTheaters = orderTheaters;
    }

    @Override
    public int getCount() {
        return mOrderTheaters.length;
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

        gridImage.setImageResource(mOrderTheaters[position].getTheater_icon_id());
        gridTitle.setText(mOrderTheaters[position].getTheater_name());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppParams.isShowIntersitialAd(mActivity)){
                    requestNewInterstitial();
                }
                String url = mOrderTheaters[position].getTheater_url();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mActivity.startActivity(i);
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