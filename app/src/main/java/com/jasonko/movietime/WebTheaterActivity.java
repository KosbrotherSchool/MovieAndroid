package com.jasonko.movietime;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.FavoriteTheater;
import com.jasonko.movietime.dao.FavoriteTheaterDao;
import com.jasonko.movietime.model.Theater;

import java.util.List;

/**
 * Created by kolichung on 9/30/15.
 */
public class WebTheaterActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progress;
    private String stringAddress;

    private CardView locateCard;
    private CardView loveCard;
    private ImageView loveImage;

    private int theater_id;
    private Theater theTheater;
    private DaoMaster.DevOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_web);
        String mUrl = getIntent().getStringExtra("theater_link");
        String title = getIntent().getStringExtra("theater_title");
        stringAddress = getIntent().getStringExtra("theater_address");


        locateCard = (CardView) findViewById(R.id.theater_locate_card);
        loveCard = (CardView) findViewById(R.id.theater_love_card);
        loveImage = (ImageView) findViewById(R.id.theater_love_image);

        theater_id = getIntent().getIntExtra("theater_id", 0);
        theTheater = Theater.getTheaterByID(theater_id);
        helper = new DaoMaster.DevOpenHelper(this, "expense", null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setBackgroundResource(R.color.movie_indicator);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

        webView = (WebView) findViewById (R.id.news_webview);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.GONE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress_value) {
                progress.setProgress(progress_value);
            }

        });

        webView.loadUrl(mUrl);

        locateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=" + stringAddress));
                startActivity(intent);
            }
        });

        loveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFollow()) {
                    deleteFollow();
                    loveImage.setImageResource(R.drawable.icon_love_white);
                    Toast.makeText(WebTheaterActivity.this, "取消我的最愛", Toast.LENGTH_SHORT).show();
                } else {
                    addFollow();
                    loveImage.setImageResource(R.drawable.icon_love_white_full);
                    Toast.makeText(WebTheaterActivity.this, "加入我的最愛", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (checkFollow()){
            loveImage.setImageResource(R.drawable.icon_love_white_full);
        }else {
            loveImage.setImageResource(R.drawable.icon_love_white);
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
        favoriteTheaterDao.delete((FavoriteTheater) favoriteTheaters.get(0));
    }

    private void addFollow() {
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FavoriteTheaterDao favoriteTheaterDao = daoSession.getFavoriteTheaterDao();
        FavoriteTheater newFavorite = new FavoriteTheater(null, theTheater.getTheater_id(),theTheater.getArea_id(),theTheater.getName(),theTheater.getAddress(), theTheater.getPhone());
        favoriteTheaterDao.insert(newFavorite);
    }

    private void showInfoDialog() {

        new AlertDialog.Builder(WebTheaterActivity.this)
                .setTitle("戲院資訊")
                .setMessage("電話："+theTheater.getPhone()+"\n"+"地址："+theTheater.getAddress())
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

    }
}
