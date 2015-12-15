package com.jasonko.movietime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.jasonko.movietime.fragments.FavoriteMovieFragment;
import com.jasonko.movietime.fragments.FavoriteTheaterFragment;

/**
 * Created by kolichung on 9/6/15.
 */
public class FollowMovieActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagers);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
        tabsStrip.setIndicatorColor(getResources().getColor(R.color.white));
        tabsStrip.setIndicatorHeight(10);
        tabsStrip.setBackgroundColor(getResources().getColor(R.color.movie_indicator));
        tabsStrip.setTextColorResource(R.color.white);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setBackgroundResource(R.color.movie_indicator);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("我的最愛");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[] { "最愛電影","最愛影城"};

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment theNewsFragment = null;
            switch (position){
                case 0:
                    theNewsFragment = FavoriteMovieFragment.newInstance();
                    break;
                case 1:
                    theNewsFragment = FavoriteTheaterFragment.newInstance();
                    break;
            }
            return theNewsFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_follow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }else if (menuItem.getItemId() == R.id.action_follow_setting){
            Intent intent = new Intent(FollowMovieActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

}