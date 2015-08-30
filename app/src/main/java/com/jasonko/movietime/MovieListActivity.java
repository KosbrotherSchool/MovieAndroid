package com.jasonko.movietime;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.jasonko.movietime.fragments.MovieGridFragment;
import com.jasonko.movietime.fragments.MovieListFragment;

/**
 * Created by kolichung on 8/28/15.
 */
public class MovieListActivity extends FragmentActivity {

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

        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[] { "本週新片", "上映中", "即將上映"};

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
                    newFragment = MovieListFragment.newInstance();
                    break;
                case 1:
                    newFragment = MovieGridFragment.newInstance(1);
                    break;
                case 2:
                    newFragment = MovieGridFragment.newInstance(3);
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