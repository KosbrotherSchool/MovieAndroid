package com.jasonko.movietime;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.jasonko.movietime.adapters.RandomYoutubeVideoAdapter;
import com.jasonko.movietime.adapters.RankMovieAdapter;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.model.MyYoutubeVideo;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

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

    private static final String[] drawer_menu_items = new String[]{
            "最近瀏覽", "我的追蹤", "我要訂票", "問題回報", "好用給個讚", "分享給好友", "關於我們", "我的設定"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //設定各個元件的對應id
        processViews();

        searchBox = (SearchBox) findViewById(R.id.searchbox);
        setSearchBar();

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

        new RankMoviesTask().execute();
        new RandomVideosTask().execute();
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

        //設定drawer中的listview的選項
        listview_drawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawer_menu_items));

    }

    private class RankMoviesTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            rankMovies = MovieAPI.getTaipeiRankMovies();
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            RankMovieAdapter videoAdapter = new RankMovieAdapter(MainActivity.this, rankMovies);
            rankRecyclerView.setAdapter(videoAdapter);
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
            RandomYoutubeVideoAdapter videoAdapter = new RandomYoutubeVideoAdapter(MainActivity.this, randomVideos);
            recommendRecyclerView.setAdapter(videoAdapter);
        }
    }


    private void setSearchBar(){
        searchBox.enableVoiceRecognition(this);
        searchBox = (SearchBox) findViewById(R.id.searchbox);
        for(int x = 0; x < 10; x++){
            SearchResult option = new SearchResult("Result " + Integer.toString(x), getResources().getDrawable(R.drawable.ic_action_mic));
            searchBox.addSearchable(option);
        }
        searchBox.setLogoText("My App");
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
                Toast.makeText(MainActivity.this, searchTerm + " Searched", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onSearchCleared() {

            }

        });
    }


    //需要處理語音搜尋時，再回來coding
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isAdded() && requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == getActivity().RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            searchBox.populateEditText(matches);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

}
