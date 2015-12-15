package com.jasonko.movietime;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonko.movietime.adapters.MoviesListOfTheaterAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.FavoriteTheater;
import com.jasonko.movietime.dao.FavoriteTheaterDao;
import com.jasonko.movietime.model.MovieTime;
import com.jasonko.movietime.model.Theater;
import com.jasonko.movietime.tool.NetworkUtil;

import java.util.ArrayList;
import java.util.List;


public class TheaterActivity extends AppCompatActivity {

//    private String stringAddress;

    private RecyclerView mRecyclerView;
    private MoviesListOfTheaterAdapter mAdapter;
    private ArrayList<MovieTime> mData;
    private int theater_id;
    private Theater theTheater;

    private ProgressBar mProgressBar;
    private TextView noNetText;

    private DaoMaster.DevOpenHelper helper;

    private CardView locateCard;
    private CardView loveCard;
    private ImageView loveImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater);

        mProgressBar = (ProgressBar) findViewById(R.id.my_progress_bar);
        noNetText = (TextView) findViewById(R.id.no_network_text);

        //set basic data of the clicked theater
        Intent intent = getIntent();

        locateCard = (CardView) findViewById(R.id.theater_locate_card);
        loveCard = (CardView) findViewById(R.id.theater_love_card);
        loveImage = (ImageView) findViewById(R.id.theater_love_image);

        theater_id = intent.getIntExtra("theater_id", 0);
        theTheater = Theater.getTheaterByID(theater_id);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(theTheater.getName());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_movielist_of_theater);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        helper = new DaoMaster.DevOpenHelper(this, "expense", null);

        if (NetworkUtil.getConnectivityStatus(TheaterActivity.this) != 0) {
            new GetMoviesTask().execute();
        }else {
            mProgressBar.setVisibility(View.GONE);
            noNetText.setVisibility(View.VISIBLE);
        }

        locateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=" + theTheater.getAddress()));
                startActivity(intent);
            }
        });

        loveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFollow()) {
                    deleteFollow();
                    loveImage.setImageResource(R.drawable.icon_love_white);
                    Toast.makeText(TheaterActivity.this, "取消我的最愛", Toast.LENGTH_SHORT).show();
                } else {
                    addFollow();
                    loveImage.setImageResource(R.drawable.icon_love_white_full);
                    Toast.makeText(TheaterActivity.this, "加入我的最愛", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (checkFollow()){
            loveImage.setImageResource(R.drawable.icon_love_white_full);
        }else {
            loveImage.setImageResource(R.drawable.icon_love_white);
        }

    }


    private class GetMoviesTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params){
            mData = MovieAPI.getTheaterMovieTimes(theater_id);
            return null;
        }

        @Override
        protected void onPostExecute(Object result){
            if (mData != null && mData.size() > 0) {
                mAdapter = new MoviesListOfTheaterAdapter(TheaterActivity.this, mData);
                mRecyclerView.setAdapter(mAdapter);
            }else {
                noNetText.setVisibility(View.VISIBLE);
            }
            mProgressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_theater, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_to_theater_info:
                //todo show dialog
                showInfoDialog();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showInfoDialog() {

        new AlertDialog.Builder(TheaterActivity.this)
                .setTitle("戲院資訊")
                .setMessage("電話："+theTheater.getPhone()+"\n"+"地址："+theTheater.getAddress())
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    }

    private boolean checkFollow() {
        if (helper!=null){
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            FavoriteTheaterDao favoriteTheaterDao = daoSession.getFavoriteTheaterDao();
            List favoriteTheaters = favoriteTheaterDao.queryBuilder().where(FavoriteTheaterDao.Properties.Theater_id.eq(theater_id)).list();
            if (favoriteTheaters.size() > 0){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    private void deleteFollow() {
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FavoriteTheaterDao favoriteTheaterDao = daoSession.getFavoriteTheaterDao();
        List favoriteTheaters = favoriteTheaterDao.queryBuilder().where(FavoriteTheaterDao.Properties.Theater_id.eq(theater_id)).list();
        favoriteTheaterDao.delete((FavoriteTheater)favoriteTheaters.get(0));
    }

    private void addFollow() {
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FavoriteTheaterDao favoriteTheaterDao = daoSession.getFavoriteTheaterDao();
        FavoriteTheater newFavorite = new FavoriteTheater(null, theTheater.getTheater_id(),theTheater.getArea_id(),theTheater.getName(),theTheater.getAddress(), theTheater.getPhone());
        favoriteTheaterDao.insert(newFavorite);
    }

}
