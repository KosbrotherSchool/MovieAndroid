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
                String url = mOrderTheaters[position].getTheater_url();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mActivity.startActivity(i);
            }
        });

        return convertView;
    }
}