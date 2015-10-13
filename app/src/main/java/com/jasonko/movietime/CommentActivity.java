package com.jasonko.movietime;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jasonko.movietime.adapters.CommentListAdapter;
import com.jasonko.movietime.api.ReviewAPI;
import com.jasonko.movietime.model.Review;
import com.jasonko.movietime.tool.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;


public class CommentActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Review> mReviews = new ArrayList<>();
    private CommentListAdapter mAdapter;
    private int movie_id;
    private String movie_title;
    private int mPage = 1;

    private TextView pointText;
    private TextView totalPeopleText;

    private LinearLayout noCommentLienar;
    private Button buttonAddComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        movie_id = intent.getIntExtra("movie_id", 0);
        movie_title = intent.getStringExtra("title");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_comment);
        toolbar.setNavigationIcon(R.drawable.icon_back_white);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(movie_title);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_comments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        pointText = (TextView) findViewById(R.id.points_text);
        totalPeopleText = (TextView) findViewById(R.id.total_people_text);
        noCommentLienar = (LinearLayout) findViewById(R.id.linear_no_comment);
        buttonAddComment = (Button) findViewById(R.id.button_comment);

        pointText.setText(intent.getStringExtra("point_str")+"分");
        totalPeopleText.setText("(共 " + intent.getStringExtra("review_size_str") + " 人評分)");

        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                new CommentTask().execute();
            }
        });

        buttonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentActivity.this, WriteCommentActivity.class);
                intent.putExtra("movie_id",movie_id);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter == null) {
            new CommentTask().execute();
        }else {
            mReviews.clear();
            mAdapter.notifyDataSetChanged();
            mPage = 1;
            new CommentTask().execute();
        }
    }

    private class CommentTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params){
            ArrayList<Review> tempReviews = ReviewAPI.getReviewsByMovieID(movie_id, mPage);
            if(tempReviews != null && tempReviews.size() > 0){
                mReviews.addAll(tempReviews);
                mPage++;
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Object result){
            if((boolean) result){
                if(mAdapter == null){
                    mAdapter = new CommentListAdapter(CommentActivity.this, mReviews);
                    mRecyclerView.setAdapter(mAdapter);
                    noCommentLienar.setVisibility(View.GONE);
                }else {
                    mAdapter.notifyDataSetChanged();
                }
            }else{
                if (mAdapter != null) {
//                    Toast.makeText(CommentActivity.this, "沒有其他評論", Toast.LENGTH_SHORT).show();
                }else {
                    noCommentLienar.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }else if (menuItem.getItemId() == R.id.action_write_comment){
            Intent intent = new Intent(CommentActivity.this, WriteCommentActivity.class);
            intent.putExtra("movie_id",movie_id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }


}
