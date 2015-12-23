package com.jasonko.movietime;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.fragments.TabHallaFragment;
import com.jasonko.movietime.fragments.TabMovieFragment;
import com.jasonko.movietime.fragments.TabOtherFragment;
import com.jasonko.movietime.fragments.TabPostFragment;
import com.jasonko.movietime.fragments.TabTheaterFragment;
import com.jasonko.movietime.model.Version;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private String currentTabId = "tab1";
    private HashMap<String, View> tabs = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        View tab1 = getLayoutInflater().inflate(R.layout.tab_item,null);
        ImageView movieImage = (ImageView) tab1.findViewById(R.id.tab_iamge);
        movieImage.setImageResource(R.drawable.tab_icon_movie_yellow);
        TextView movieText = (TextView) tab1.findViewById(R.id.tab_title);
        movieText.setText("電影");
        movieText.setTextColor(getResources().getColor(R.color.movie_indicator));
        tabs.put("tab1", tab1);
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(tab1),
                TabMovieFragment.class, null);

        View tab2 = getLayoutInflater().inflate(R.layout.tab_item,null);
        ImageView theaterImage = (ImageView) tab2.findViewById(R.id.tab_iamge);
        theaterImage.setImageResource(R.drawable.tab_icon_theater_gray);
        TextView theaterText = (TextView) tab2.findViewById(R.id.tab_title);
        theaterText.setText("影院");
        theaterText.setTextColor(getResources().getColor(R.color.gray_text));
        tabs.put("tab2", tab2);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(tab2),
                TabTheaterFragment.class, null);

        View tab3 = getLayoutInflater().inflate(R.layout.tab_item,null);
        ImageView hallaImage = (ImageView) tab3.findViewById(R.id.tab_iamge);
        hallaImage.setImageResource(R.drawable.tab_icon_discuss_gray);
        TextView hallaText = (TextView) tab3.findViewById(R.id.tab_title);
        hallaText.setText("哈拉區");
        hallaText.setTextColor(getResources().getColor(R.color.gray_text));
        tabs.put("tab3", tab3);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator(tab3),
                TabHallaFragment.class, null);


        View tab4 = getLayoutInflater().inflate(R.layout.tab_item,null);
        ImageView blogImage = (ImageView) tab4.findViewById(R.id.tab_iamge);
        blogImage.setImageResource(R.drawable.tab_icon_blog_post_gray);
        TextView blogText = (TextView) tab4.findViewById(R.id.tab_title);
        blogText.setText("影評");
        blogText.setTextColor(getResources().getColor(R.color.gray_text));
        tabs.put("tab4", tab4);
        mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator(tab4),
                TabPostFragment.class, null);

        View tab5 = getLayoutInflater().inflate(R.layout.tab_item,null);
        ImageView otherImage = (ImageView) tab5.findViewById(R.id.tab_iamge);
        otherImage.setImageResource(R.drawable.tab_icon_other_gray);
        TextView otherText = (TextView) tab5.findViewById(R.id.tab_title);
        otherText.setText("其他");
        otherText.setTextColor(getResources().getColor(R.color.gray_text));
        tabs.put("tab5", tab5);
        mTabHost.addTab(mTabHost.newTabSpec("tab5").setIndicator(tab5),
                TabOtherFragment.class, null);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                View tab = tabs.get(tabId);
                ImageView  tabImage = (ImageView) tab.findViewById(R.id.tab_iamge);
                TextView tabText = (TextView) tab.findViewById(R.id.tab_title);
                tabText.setTextColor(getResources().getColor(R.color.movie_indicator));
                if (tabId.equals("tab1")) {
                    tabImage.setImageResource(R.drawable.tab_icon_movie_yellow);
                }
                if (tabId.equals("tab2")) {
                    tabImage.setImageResource(R.drawable.tab_icon_theater_yellow);
                }
                if (tabId.equals("tab3")) {
                    tabImage.setImageResource(R.drawable.tab_icon_discuss_yellow);
                }
                if (tabId.equals("tab4")) {
                    tabImage.setImageResource(R.drawable.tab_icon_blog_post_yellow);
                }
                if (tabId.equals("tab5")) {
                    tabImage.setImageResource(R.drawable.tab_icon_other_yellow);
                }

                View lastTab = tabs.get(currentTabId);
                ImageView  lastTabImage = (ImageView) lastTab.findViewById(R.id.tab_iamge);
                TextView lastTabText = (TextView) lastTab.findViewById(R.id.tab_title);
                lastTabText.setTextColor(getResources().getColor(R.color.gray_text));
                if (currentTabId.equals("tab1")) {
                    lastTabImage.setImageResource(R.drawable.tab_icon_movie_gray);
                }
                if (currentTabId.equals("tab2")) {
                    lastTabImage.setImageResource(R.drawable.tab_icon_theater_gray);
                }
                if (currentTabId.equals("tab3")) {
                    lastTabImage.setImageResource(R.drawable.tab_icon_discuss_gray);
                }
                if (currentTabId.equals("tab4")) {
                    lastTabImage.setImageResource(R.drawable.tab_icon_blog_post_gray);
                }
                if (currentTabId.equals("tab5")) {
                    lastTabImage.setImageResource(R.drawable.tab_icon_other_gray);
                }

                currentTabId = tabId;
            }
        });

        checkCurrentVersion();

    }

    private void checkCurrentVersion() {

        new GetVersionCodeTask().execute();

    }


    private class GetVersionCodeTask extends AsyncTask {

        Version currentVersion;

        @Override
        protected Object doInBackground(Object[] params) {
            currentVersion = MovieAPI.getVersion();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (currentVersion != null) {
                try {
                    PackageManager manager = getApplicationContext().getPackageManager();
                    PackageInfo info = manager.getPackageInfo(
                            getApplicationContext().getPackageName(), 0);
                    int verCode = info.versionCode;

                    SharedPreferences mPref = getPreferences(0);
                    int prefVersion = mPref.getInt("version code", 0);

                    if(verCode != currentVersion.getVersionCode() && verCode < currentVersion.getVersionCode()){
                        if(prefVersion == currentVersion.getVersionCode()){
                            boolean isPopGetNewDialog = mPref.getBoolean("is pop new version dialog", true);
                            if (isPopGetNewDialog){
                                popGetNewDialog("version code", currentVersion.getVersionCode(), currentVersion.getVersionName(), currentVersion.getVersionContent());
                            }
                        }else {
                            popGetNewDialog("version code", currentVersion.getVersionCode(), currentVersion.getVersionName(), currentVersion.getVersionContent());
                        }
                    }

                }catch (Exception e){

                }


            }
        }
    }

    private void popGetNewDialog(String versionCodeKey, int versionCode, String verstionName, String versionContent) {

        SharedPreferences mPref = getPreferences(0);
        mPref.edit().putInt(versionCodeKey, versionCode).commit();

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(verstionName);
        dialog.setMessage(Html.fromHtml(versionContent));
        dialog.setNegativeButton("不再通知", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                SharedPreferences mPref = getPreferences(0);
                mPref.edit().putBoolean("is pop new version dialog", false).commit();
            }

        });
        dialog.setPositiveButton("更新",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String url = "https://play.google.com/store/apps/details?id=com.jasonko.movietime";
                Intent intentGood = new Intent(Intent.ACTION_VIEW);
                intentGood.setData(Uri.parse(url));
                startActivity(intentGood);
            }

        });
        dialog.setNeutralButton("稍後通知", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                SharedPreferences mPref = getPreferences(0);
                mPref.edit().putBoolean("is pop new version dialog", true).commit();
            }

        });
        dialog.show();

    }

}
