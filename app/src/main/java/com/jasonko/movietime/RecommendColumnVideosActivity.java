package com.jasonko.movietime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.astuetz.PagerSlidingTabStrip;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.fragments.VideosGridFragment;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.MyYoutubeVideo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kolichung on 8/28/15.
 */
public class RecommendColumnVideosActivity extends AppCompatActivity {

    private ArrayList<String> tabTitles = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<MyYoutubeVideo> myYoutubeVideos;
    private HashMap<String, ArrayList<MyYoutubeVideo>> mHashMap = new HashMap<>();
    private ViewPager viewPager;
    private PagerSlidingTabStrip tabsStrip;
    private int column_id;
    private String image_link;
    private ImageLoader imageLoader;
    private ImageView imageView;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_column_videos);
        column_id = getIntent().getIntExtra("column_id", 0);
        image_link = getIntent().getStringExtra("image_link");
        mProgressBar = (ProgressBar) findViewById(R.id.my_progress_bar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("cek", "home selected");
//            }
//        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Give the PagerSlidingTabStrip the ViewPager
        tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setIndicatorColor(getResources().getColor(R.color.white));
        tabsStrip.setIndicatorHeight(10);
        tabsStrip.setBackgroundColor(getResources().getColor(R.color.movie_indicator));
        tabsStrip.setTextColorResource(R.color.white);

        imageView = (ImageView) findViewById(R.id.activity_recommend_video_image);
        imageLoader = new ImageLoader(this, 300);
        imageLoader.DisplayImage(image_link, imageView);
        new NewsTask().execute();
    }

    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public Fragment getItem(int position) {
            return VideosGridFragment.newInstance(mHashMap.get(keys.get(position)));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles.get(position);
        }
    }

    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            myYoutubeVideos = MovieAPI.getColumnYoutubeVideos(column_id);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            for (int i=0; i< myYoutubeVideos.size(); i++){
                MyYoutubeVideo video = myYoutubeVideos.get(i);
                if (mHashMap.get(Integer.toString(video.getYoutube_sub_column_id())) != null) {
                    mHashMap.get(Integer.toString(video.getYoutube_sub_column_id())).add(video);
                }else {
                    ArrayList<MyYoutubeVideo> arrayVideos = new ArrayList<>();
                    arrayVideos.add(video);
                    mHashMap.put(Integer.toString(video.getYoutube_sub_column_id()), arrayVideos);
                    tabTitles.add(video.getSubColumnName());
                    keys.add(Integer.toString(video.getYoutube_sub_column_id()));
                }
            }

            if (tabTitles.size()>0) {
                viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
                tabsStrip.setViewPager(viewPager);
            }

            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
//            Toast.makeText(this,"toast",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
