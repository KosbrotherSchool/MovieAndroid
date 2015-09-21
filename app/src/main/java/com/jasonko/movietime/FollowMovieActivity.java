package com.jasonko.movietime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.jasonko.movietime.adapters.FollowMovieGridAdapter;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.FollowMovie;
import com.jasonko.movietime.dao.FollowMovieDao;

import java.util.ArrayList;

/**
 * Created by kolichung on 9/6/15.
 */
public class FollowMovieActivity extends AppCompatActivity {

    private GridView mGridView;
    private FollowMovieGridAdapter followMovieGridAdapter;
    private ArrayList<FollowMovie> mMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        mGridView = (GridView) findViewById(R.id.fragment_gridview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setBackgroundResource(R.color.movie_indicator);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("我的追蹤電影");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "expense", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FollowMovieDao movieDao = daoSession.getFollowMovieDao();

        mMovies = (ArrayList) movieDao.queryBuilder().orderDesc(FollowMovieDao.Properties.Update_date).list();
        followMovieGridAdapter = new FollowMovieGridAdapter(this, mMovies);
        mGridView.setAdapter(followMovieGridAdapter);

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

        }
        return super.onOptionsItemSelected(menuItem);
    }

}