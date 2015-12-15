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

import com.jasonko.movietime.R;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.Trailer;

import java.util.ArrayList;

/**
 * Created by kolichung on 9/3/15.
 */
public class TrailerGridAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<Trailer> mTrailers;
    private ImageLoader mImageLoader;

    public TrailerGridAdapter(Activity activity, ArrayList<Trailer> trailers) {
        mActivity = activity;
        mTrailers = trailers;
        mImageLoader = new ImageLoader(this.mActivity);
    }

    @Override
    public int getCount() {
        return mTrailers.size();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_grid_video, null);
        }
        ImageView gridImage = (ImageView) convertView.findViewById(R.id.item_video_image);
        TextView gridTitle = (TextView) convertView.findViewById(R.id.item_video_title);

        mImageLoader.DisplayImage(mTrailers.get(position).getPicLink(), gridImage);
        gridTitle.setText(mTrailers.get(position).getTitle());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mTrailers.get(position).getYoutube_link();
                Intent intentGood = new Intent(Intent.ACTION_VIEW);
                intentGood.setData(Uri.parse(url));
                mActivity.startActivity(intentGood);
            }
        });
        return convertView;
    }

}