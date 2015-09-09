package com.jasonko.movietime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.fragments.PhotoFragment;
import com.jasonko.movietime.model.Photo;

import java.util.ArrayList;

/**
 * Created by kolichung on 9/3/15.
 */
public class MoviePhotosActivity  extends AppCompatActivity {

    private int photo_size = 0;
    private int movie_id;
    private String initial_photo_url;
    private ArrayList<Photo> photos;
    private SampleFragmentPagerAdapter mPagerAdapter;
    private ViewPager viewPager;
    private TextView textNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        photo_size = getIntent().getIntExtra("photo_size", 0);
        initial_photo_url = getIntent().getStringExtra("big_photo_url");
        movie_id = getIntent().getIntExtra("movie_id", 0);

        textNum = (TextView) findViewById(R.id.photo_num_text);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textNum.setText(Integer.toString(position+ 1) + " / " + Integer.toString(photos.size()+ 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        new NewsTask().execute();
    }

    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return photos.size() + 1;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return PhotoFragment.newInstance(initial_photo_url);
            }else{
                return PhotoFragment.newInstance(photos.get(position-1).getPhoto_link());
            }
        }


    }

    private class NewsTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            photos = MovieAPI.getMoviePhotosByID(movie_id);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            mPagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(mPagerAdapter);
            textNum.setText(Integer.toString(viewPager.getCurrentItem() + 1) + " / " + Integer.toString(photos.size() + 1));
        }
    }

}