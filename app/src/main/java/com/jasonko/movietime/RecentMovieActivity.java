package com.jasonko.movietime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.jasonko.movietime.adapters.RecentMovieAdapter;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.RecentMovie;
import com.jasonko.movietime.dao.RecentMovieDao;

import java.util.ArrayList;

/**
 * Created by kolichung on 9/6/15.
 */
public class RecentMovieActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<RecentMovie> mMovies = new ArrayList<>();
    private RecentMovieAdapter recentMovieAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_column);
        mProgressBar = (ProgressBar) findViewById(R.id.my_progress_bar);
        mProgressBar.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("最近瀏覽電影");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_fragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "expense", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecentMovieDao movieDao = daoSession.getRecentMovieDao();

        mMovies = (ArrayList) movieDao.queryBuilder().orderDesc(RecentMovieDao.Properties.Update_date).list();
        recentMovieAdapter = new RecentMovieAdapter(RecentMovieActivity.this, mMovies);
        recyclerView.setAdapter(recentMovieAdapter);

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