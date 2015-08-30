package com.jasonko.movietime;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.jasonko.movietime.fragments.MovieRankFragment;

/**
 * Created by kolichung on 8/27/15.
 */
public class MovieRankActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagers);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
        tabsStrip.setIndicatorColor(Color.parseColor("#ff2EB9FF"));
    }

    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        final int PAGE_COUNT = 5;
        private String tabTitles[] = new String[] { "台北票房", "北美票房", "周冠軍", "期待榜", "滿意榜" };

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment newFragment = null;
            switch (position){
                case 0:
                    newFragment = MovieRankFragment.newInstance(position + 1);
                    break;
                case 1:
                    newFragment = MovieRankFragment.newInstance(position + 1);
                    break;
                case 2:
                    newFragment = MovieRankFragment.newInstance(position + 1);
                    break;
                case 3:
                    newFragment = MovieRankFragment.newInstance(position + 2);
                    break;
                case 4:
                    newFragment = MovieRankFragment.newInstance(position + 2);
                    break;
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
