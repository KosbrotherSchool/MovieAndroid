package com.jasonko.movietime.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonko.movietime.AboutUsActivity;
import com.jasonko.movietime.FollowMovieActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.SettingActivity;
import com.jasonko.movietime.model.DrawerItem;
import com.jasonko.movietime.tool.GMailSender;

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
                        Intent intentSetting = new Intent(mActivity, SettingActivity.class);
                        mActivity.startActivity(intentSetting);
                        break;
                    case 2:
                        showProblemDialog();
                        break;
                    case 3:
                        String url = "https://play.google.com/store/apps/details?id=com.jasonko.movietime";
                        Intent intentGood = new Intent(Intent.ACTION_VIEW);
                        intentGood.setData(Uri.parse(url));
                        mActivity.startActivity(intentGood);
                        break;
                    case 4:
                        Intent intentAbout = new Intent(mActivity, AboutUsActivity.class);
                        mActivity.startActivity(intentAbout);
                        break;
                }


            }
        });

        return convertView;
    }

    private void showProblemDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
        View replyView = mActivity.getLayoutInflater().inflate(R.layout.mail_content_view, null);
        final EditText contentEditText = (EditText) replyView.findViewById(R.id.mail_content_edit_text);
        dialog.setView(replyView);
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }

        });
        dialog.setPositiveButton("送出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (!contentEditText.getText().toString().equals("")) {
                    String[] strings = {"電影即時通問題或建議(Android)", contentEditText.getText().toString()};
                    new SendMailTask().execute(strings);
                } else {
                    Toast.makeText(mActivity, "空白內容無法傳送", Toast.LENGTH_SHORT).show();
                }
            }

        });
        dialog.show();

    }

    private class SendMailTask extends AsyncTask<String, Void, String> {

        Toast mailToast;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mailToast = Toast.makeText(mActivity,"傳送中...", Toast.LENGTH_SHORT);
            mailToast.show();
        }

        @Override
        protected String doInBackground(String... params) {
            GMailSender sender = new GMailSender("movietimeautomail@gmail.com", "MovieTime2015");
            try {
                sender.sendMail(params[0],
                        params[1],
                        "movietimeautomail@gmail.com", "kosbrotherschool@gmail.com");
            }catch (Exception e){
                return "error";
            }
            return "ok";
        }

        @Override
        protected void onPostExecute(String s) {
            mailToast.cancel();
            if (s.equals("ok")){
                Toast.makeText(mActivity,"已成功上傳", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(mActivity,"未成功上傳", Toast.LENGTH_SHORT).show();
            }
        }
    }
}