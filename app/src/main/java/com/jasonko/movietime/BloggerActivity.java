package com.jasonko.movietime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.jasonko.movietime.adapters.BloggerGridAdapter;

/**
 * Created by kolichung on 9/23/15.
 */
public class BloggerActivity extends AppCompatActivity {

    private GridView mGridView;
    private BloggerGridAdapter mBloggerGridAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        mGridView = (GridView) findViewById(R.id.fragment_gridview);
        mBloggerGridAdatper = new BloggerGridAdapter(BloggerActivity.this, AppParams.bloggers);
        mGridView.setAdapter(mBloggerGridAdatper);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setBackgroundResource(R.color.deep_blue);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("電影部落格");

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