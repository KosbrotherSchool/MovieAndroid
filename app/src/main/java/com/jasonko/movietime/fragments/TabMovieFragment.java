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
import com.jasonko.movietime.FavoriteMovieActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.SearchResultActivity;

/**
 * Created by kolichung on 12/14/15.
 */
public class TabMovieFragment extends Fragment {

    public static TabMovieFragment newInstance() {
        TabMovieFragment fragment = new TabMovieFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.activity_pagers, container, false);
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getChildFragmentManager()));

        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) root.findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
        tabsStrip.setIndicatorColor(getResources().getColor(R.color.movie_indicator));
        tabsStrip.setIndicatorHeight(10);
        tabsStrip.setTextColorResource(R.color.white);
        tabsStrip.setBackgroundColor(getResources().getColor(R.color.gray_background));

        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.icon_search);
        TextView titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleText.setText("電影");

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
        if (menuItem.getItemId() == android.R.id.home) {
            Intent searchIntent = new Intent(getActivity(), SearchResultActivity.class);
            startActivity(searchIntent);
        }
        if (menuItem.getItemId() == R.id.action_love) {
            Intent searchIntent = new Intent(getActivity(), FavoriteMovieActivity.class);
            startActivity(searchIntent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        final int PAGE_COUNT = 4;
        private String tabTitles[] = new String[] { "上映中", "本週","二輪", "未上映"};

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
                    newFragment = MovieGridFragment.newInstance(1);
                    break;
                case 1:
                    newFragment = MovieGridFragment.newInstance(4);
                    break;
                case 2:
                    newFragment = MovieGridFragment.newInstance(2);
                    break;
                case 3:
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