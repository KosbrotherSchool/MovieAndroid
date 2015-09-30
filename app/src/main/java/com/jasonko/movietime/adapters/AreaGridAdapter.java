package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jasonko.movietime.R;
import com.jasonko.movietime.TheatersOfAreaActivity;
import com.jasonko.movietime.model.Area;

import java.util.ArrayList;

/**
 * Created by kolichung on 9/14/15.
 */
public class AreaGridAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<Area> mAreas;

    public AreaGridAdapter(Activity activity, ArrayList<Area> areas) {
        mActivity = activity;
        mAreas = areas;
    }

    @Override
    public int getCount() {
        return mAreas.size();
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
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.grid_item, null);
        }
        TextView gridTitle = (TextView) convertView.findViewById(R.id.textArea);
        gridTitle.setText(mAreas.get(position).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, TheatersOfAreaActivity.class);
                intent.putExtra("area_id", mAreas.get(position).getArea_id());
                intent.putExtra("area_name", mAreas.get(position).getName());
                mActivity.startActivity(intent);
            }
        });

        return convertView;
    }
}
