package com.jasonko.movietime;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jasonko.movietime.adapters.DrawerListAdapter;
import com.jasonko.movietime.adapters.RandomYoutubeVideoAdapter;
import com.jasonko.movietime.adapters.RankMovieAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.model.MyYoutubeVideo;
import com.jasonko.movietime.tool.NetworkUtil;
import com.quinny898.library.persistentsearch.SearchBox;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private LinearLayout lLayout_drawer;
    private ListView listview_drawer;
    private SearchBox searchBox;
    private RecyclerView rankRecyclerView;
    private RecyclerView recommendRecyclerView;

    private ArrayList<Movie> rankMovies = new ArrayList<>();
    private ArrayList<MyYoutubeVideo> randomVideos = new ArrayList<>();

    private CardView newsCardView;
    private CardView theaterCardView;
    private CardView movieCardView;
    private CardView creditCardView;
    private CardView ticketCardView;
    private CardView moreRankCardView;
    private CardView moreRecommendVideoCardView;

    private ProgressBar movieProgress;
    private ProgressBar videoProgress;
    private TextView movieText;
    private TextView videoText;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //設定各個元件的對應id
        processViews();

        setSearchBar();
//        setAdView();

        rankRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_rank);
        rankRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rankRecyclerView.setLayoutManager(mLayoutManager);

        recommendRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_recommend);
        recommendRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recommendRecyclerView.setLayoutManager(mLayoutManager2);

        if (NetworkUtil.getConnectivityStatus(MainActivity.this) != 0) {
            new RankMoviesTask().execute();
            new RandomVideosTask().execute();
        }else {
            movieProgress.setVisibility(View.GONE);
            videoProgress.setVisibility(View.GONE);
            movieText.setVisibility(View.VISIBLE);
            videoText.setVisibility(View.VISIBLE);
        }
    }

    private void setAdView() {
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mAdView.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                mAdView.setVisibility(View.GONE);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //設定各個元件的對應id
    protected void processViews(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        listview_drawer = (ListView)findViewById(R.id.listview_drawer);
        lLayout_drawer = (LinearLayout)findViewById(R.id.lLayout_drawer);
        newsCardView = (CardView) findViewById(R.id.news_card_view);
        theaterCardView = (CardView) findViewById(R.id.theater_card_view);
        movieCardView = (CardView) findViewById(R.id.movie_card_view);
        creditCardView = (CardView) findViewById(R.id.credit_card_view);
        ticketCardView = (CardView) findViewById(R.id.ticket_card_view);
        moreRankCardView = (CardView) findViewById(R.id.more_rank_card_view);
        moreRecommendVideoCardView = (CardView) findViewById(R.id.more_recommend_video_card_view);
        movieProgress = (ProgressBar) findViewById(R.id.main_movie_progress);
        videoProgress = (ProgressBar) findViewById(R.id.main_video_progress);
        movieText = (TextView) findViewById(R.id.main_movie_text);
        videoText = (TextView) findViewById(R.id.main_video_text);

        //設定drawer中的listview的選項
        DrawerListAdapter mAdapter = new DrawerListAdapter(this, AppParams.drawerItems);
        listview_drawer.setAdapter(mAdapter);

        newsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(newIntent);
            }
        });

        theaterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this, TheatersActivity.class);
                startActivity(newIntent);
            }
        });

        ticketCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this, TicketActivity.class);
                startActivity(newIntent);
            }
        });

        movieCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this, MovieListActivity.class);
                startActivity(newIntent);
            }
        });

        creditCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this, CreditCardActivity.class);
                startActivity(newIntent);
            }
        });

        moreRankCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this, MovieRankActivity.class);
                startActivity(newIntent);
            }
        });

        moreRecommendVideoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this, RecommendColumnActivity.class);
                startActivity(newIntent);
            }
        });
    }



    private class RankMoviesTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            rankMovies = MovieAPI.getTaipeiRankMovies(1);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (rankMovies != null && rankMovies.size() > 0) {
                RankMovieAdapter videoAdapter = new RankMovieAdapter(MainActivity.this, rankMovies);
                rankRecyclerView.setAdapter(videoAdapter);
                movieProgress.setVisibility(View.GONE);
            }else{
                movieProgress.setVisibility(View.GONE);
                movieText.setVisibility(View.VISIBLE);
            }
        }
    }

    private class RandomVideosTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            randomVideos = MovieAPI.getRandomYoutubeVideos();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (randomVideos!=null && randomVideos.size() > 0) {
                RandomYoutubeVideoAdapter videoAdapter = new RandomYoutubeVideoAdapter(MainActivity.this, randomVideos);
                recommendRecyclerView.setAdapter(videoAdapter);
                videoProgress.setVisibility(View.GONE);
            }else {
                videoProgress.setVisibility(View.GONE);
                videoText.setVisibility(View.VISIBLE);
            }
        }
    }


    private void setSearchBar(){
        searchBox = (SearchBox) findViewById(R.id.searchbox);
        searchBox.enableVoiceRecognition(this);
//        for(int x = 0; x < 10; x++){
//            SearchResult option = new SearchResult("Result " + Integer.toString(x), getResources().getDrawable(R.drawable.ic_action_mic));
//            searchBox.addSearchable(option);
//        }
        searchBox.setLogoText("電影即時通");
        searchBox.setMenuListener(new SearchBox.MenuListener() {
            @Override
            public void onMenuClick() {
                //Hamburger has been clicked
                mDrawerLayout.openDrawer(lLayout_drawer);//點擊menu icon時就會跳出drawer
            }

        });
        searchBox.setSearchListener(new SearchBox.SearchListener() {

            @Override
            public void onSearchOpened() {
                //Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                //Use this to un-tint the screen
            }

            @Override
            public void onSearchTermChanged() {
                //React to the search term changing
                //Called after it has updated results
            }

            @Override
            public void onSearch(String searchTerm) {
//                Toast.makeText(MainActivity.this, searchTerm + " Searched", Toast.LENGTH_LONG).show();
                Intent newIntent = new Intent(MainActivity.this, SearchResultActivity.class);
                newIntent.putExtra("query", searchTerm);
                startActivity(newIntent);
            }

            @Override
            public void onSearchCleared() {

            }

        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isAdded() && requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == this.RESULT_OK) {
            // get first match and move to search result activity
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            Toast.makeText(MainActivity.this, matches.get(0) + " Searched", Toast.LENGTH_LONG).show();
            Intent newIntent = new Intent(MainActivity.this, SearchResultActivity.class);
            newIntent.putExtra("query",matches.get(0));
            startActivity(newIntent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isAdded() {
        searchBox.clearResults();
        return true;
    }


}
