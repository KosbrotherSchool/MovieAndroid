package com.jasonko.movietime.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.jasonko.movietime.FavoriteTheaterActivity;
import com.jasonko.movietime.R;

/**
 * Created by kolichung on 12/14/15.
 */
public class TabTheaterFragment extends Fragment {

    public static TabTheaterFragment newInstance() {
        TabTheaterFragment fragment = new TabTheaterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.activity_pagers, container, false);
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        viewPager.setAdapter(new TheaterFragmentPagerAdapter(getChildFragmentManager()));

        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) root.findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
        tabsStrip.setIndicatorColor(getResources().getColor(R.color.movie_indicator));
        tabsStrip.setIndicatorHeight(10);
        tabsStrip.setTextColorResource(R.color.white);
        tabsStrip.setBackgroundColor(getResources().getColor(R.color.gray_background));

        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        TextView titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleText.setText("影院");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_love) {
            Intent searchIntent = new Intent(getActivity(), FavoriteTheaterActivity.class);
            startActivity(searchIntent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public class TheaterFragmentPagerAdapter extends FragmentStatePagerAdapter{
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[]{"地區", "時刻快查"};

        public TheaterFragmentPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getCount(){
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position){
            Fragment theaterFragment = null ;
            if(position == 0)
                theaterFragment = AreaFragment.newInstance();
            else if(position == 1)
                theaterFragment = QuickTimeFragment.newInstance();

            return theaterFragment;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return tabTitles[position];
        }

    }

}
