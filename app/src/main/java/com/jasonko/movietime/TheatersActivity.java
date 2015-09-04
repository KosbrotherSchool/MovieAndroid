package com.jasonko.movietime;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonko.movietime.adapters.FragmentAdapter;
import com.jasonko.movietime.fragments.AreaFragment;

import java.util.ArrayList;
import java.util.List;


public class TheatersActivity extends FragmentActivity {


    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private ViewPager mViewPager;

    //tab底下的引導線
    private ImageView iv_tab_line;
    private TextView tv_tab_area, tv_tab_theaters_lately;

    private AreaFragment mAreaFragment;
    private TheaterLatelyFragment mTheaterLatelyFragment;


    //ViewPager的當前選中頁面
    private int currentIndex;
    private int screenWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theaters);

        processViews();
        init();

    }


    //處理元件id的設定
    protected void processViews(){
        iv_tab_line = (ImageView) findViewById(R.id.iv_tab_line);
        mViewPager = (ViewPager) findViewById(R.id.viewPager_theaters);
        tv_tab_area = (TextView) findViewById(R.id.tv_tab_area);
        tv_tab_theaters_lately = (TextView) findViewById(R.id.tv_tab_theaters_lately);

    }



    //設定Fragment
    protected void init(){
        mAreaFragment = new AreaFragment();
        mTheaterLatelyFragment = new TheaterLatelyFragment();


        mFragmentList.add(mAreaFragment);
        mFragmentList.add(mTheaterLatelyFragment);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);

        //是在哪把最近瀏覽戲院的文字調整為藍色的呢？
        tv_tab_theaters_lately.setTextColor(Color.BLACK);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /**
                 * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
                 * offsetPixels:当前页面偏移的像素位置
                 */
            }

            @Override
            public void onPageSelected(int position) {
                tv_tab_area.setTextColor(Color.BLACK);
                tv_tab_theaters_lately.setTextColor(Color.BLACK);


                switch (position) {
                    case 0:
                        tv_tab_area.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        tv_tab_theaters_lately.setTextColor(Color.BLUE);
                        break;
                }
                currentIndex = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /**
                 * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
                 */
            }
        });




    }


}
