package com.jasonko.movietime;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.astuetz.PagerSlidingTabStrip;
import com.jasonko.movietime.fragments.AreaFragment;



public class TheatersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagers);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TheaterFragmentPagerAdapter(getSupportFragmentManager()));

        //get the PagerSlidingTabStrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);
        //indicator 指pager底下的那條白線
        tabStrip.setIndicatorColor(getResources().getColor(R.color.white));
        tabStrip.setIndicatorHeight(10);
        tabStrip.setTextColorResource(R.color.white);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("戲院資訊");


    }

    public class TheaterFragmentPagerAdapter extends FragmentStatePagerAdapter{
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[]{"地區", "最近瀏覽過的戲院"};

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
                theaterFragment = new AreaFragment();
            else if(position == 1)
                theaterFragment = new TheaterLatelyFragment();

            return theaterFragment;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return tabTitles[position];
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
        }
        return super.onOptionsItemSelected(menuItem);
    }




}
