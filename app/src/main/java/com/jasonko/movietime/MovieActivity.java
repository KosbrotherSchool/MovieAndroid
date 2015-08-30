package com.jasonko.movietime;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.jasonko.movietime.fragments.MovieInfoFragment;
import com.jasonko.movietime.fragments.MovieTimeFragment;

/**
 * Created by kolichung on 8/26/15.
 */
public class MovieActivity extends FragmentActivity {

    private int mMovieID;
    private int mAreaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagers);
        mMovieID = getIntent().getIntExtra("movie_id", 1);
        mAreaID = getIntent().getIntExtra("area_id", 1);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
        tabsStrip.setIndicatorColor(Color.parseColor("#ffFF9245"));
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[]{"簡介", "電影時刻"};

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment newFragment;
            if (position == 0){
                newFragment = MovieInfoFragment.newInstance(mMovieID);
            }else {
                newFragment = MovieTimeFragment.newInstance(mMovieID, mAreaID);
            }
            return newFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
}