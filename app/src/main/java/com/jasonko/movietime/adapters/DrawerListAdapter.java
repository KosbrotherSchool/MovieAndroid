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
import android.widget.Toast;

import com.jasonko.movietime.AboutUsActivity;
import com.jasonko.movietime.CreditCardActivity;
import com.jasonko.movietime.FollowMovieActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.SettingActivity;
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

                switch (position) {
                    case 0:
                        Intent intentFollow = new Intent(mActivity, FollowMovieActivity.class);
                        mActivity.startActivity(intentFollow);
                        break;
                    case 1:
                        Intent intentReport = new Intent(Intent.ACTION_SEND);
                        intentReport.setType("text/plain");
                        intentReport.putExtra(Intent.EXTRA_EMAIL, new String[]{"kosbrotherschool@gmail.com"});
                        intentReport.putExtra(Intent.EXTRA_SUBJECT, "問題回報：(from 電影即時通 APP)");
                        try {
                            mActivity.startActivity(Intent.createChooser(intentReport, "傳送 mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(mActivity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        String url = "https://play.google.com/store/apps/details?id=com.jasonko.movietime";
                        Intent intentGood = new Intent(Intent.ACTION_VIEW);
                        intentGood.setData(Uri.parse(url));
                        mActivity.startActivity(intentGood);
                        break;
                    case 3:
                        Intent intentShare = new Intent(Intent.ACTION_SEND);
                        intentShare.setType("text/plain");
                        intentShare.putExtra(Intent.EXTRA_SUBJECT, "電影即時通 APP !");
                        intentShare.putExtra(Intent.EXTRA_TEXT, "推薦給您, [電影即時通]APP 找電影一點通~  https://play.google.com/store/apps/details?id=com.jasonko.movietime");
                        try {
                            mActivity.startActivity(Intent.createChooser(intentShare, "分享 ..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(mActivity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 4:
                        Intent intentCredit = new Intent(mActivity, CreditCardActivity.class);
                        mActivity.startActivity(intentCredit);
                        break;
                    case 5:
                        Intent intentAbout = new Intent(mActivity, AboutUsActivity.class);
                        mActivity.startActivity(intentAbout);
                        break;
                    case 6:
                        Intent intentSetting = new Intent(mActivity, SettingActivity.class);
                        mActivity.startActivity(intentSetting);
                        break;
                }


            }
        });

        return convertView;
    }
}