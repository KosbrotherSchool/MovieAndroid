package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.model.DrawerItem;

/**
 * Created by kolichung on 9/1/15.
 */
public class DrawerListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DrawerItem[] mDrawerItems;

    public DrawerListAdapter(Activity activity, DrawerItem[] drawerItems) {
        mActivity = activity;
        mDrawerItems = drawerItems;
    }

    @Override
    public int getCount() {
        return mDrawerItems.length;
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_drawer, null);
        }
        ImageView gridImage = (ImageView) convertView.findViewById(R.id.drawer_image);
        TextView gridTitle = (TextView) convertView.findViewById(R.id.drawer_title);

        gridImage.setImageResource(mDrawerItems[position].getIcon_id());
        gridTitle.setText(mDrawerItems[position].getTitle());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}