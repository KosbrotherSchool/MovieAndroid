package com.jasonko.movietime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;


public class MainActivity extends AppCompatActivity {
    private Button btn_theaters;
    private SearchBox searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                Toast.makeText(MainActivity.this, "Menu click", Toast.LENGTH_LONG).show();
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
