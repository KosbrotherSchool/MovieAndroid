package com.jasonko.movietime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.Toast;

import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;


public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private LinearLayout lLayout_drawer;
    private ListView listview_drawer;

    private static final String[] drawer_menu_items = new String[]{
            "最近瀏覽", "我的追蹤", "我要訂票", "問題回報", "好用給個讚", "分享給好友", "關於我們", "我的設定"};



    private Button btn_theaters;
    private SearchBox searchBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //設定各個元件的對應id
        processViews();


        searchBox = (SearchBox) findViewById(R.id.searchbox);
        btn_theaters = (Button) findViewById(R.id.btn_theaters);
        setSearchBar();


        //打開戲院的activity
        btn_theaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_theaters = new Intent();
                intent_theaters.setClass(MainActivity.this, TheatersActivity.class);
                startActivity(intent_theaters);
            }
        });
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
