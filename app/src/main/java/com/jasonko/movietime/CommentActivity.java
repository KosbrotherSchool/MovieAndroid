package com.jasonko.movietime;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonko.movietime.api.ReviewAPI;
import com.jasonko.movietime.model.Review;
import com.jasonko.movietime.tool.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;


public class CommentActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Review> mData;
 //   CommentsListAdapter mAdapter;
    private int movie_id;
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        movie_id = intent.getIntExtra("movie_id", 0);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_comment);
//        toolbar.setNavigationIcon(R.drawable.icon_back_white);
//        toolbar.setTitleTextColor(0xFFFFFFFF);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("影片評論");

//        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_comments);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);

//        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//                new CommentTask().execute();
//            }
//        });

        setTestReviews();
//        mAdapter = new CommentsListAdapter(this, mData);
//        mRecyclerView.setAdapter(mAdapter);

    }

    private void setTestReviews(){
        Review review1 = new Review(1,2,"larry", "testTitle", "test, test, test!", "2015/10/02");
        mData.add(review1);

        Review review2 = new Review(2,3,"kerli", "testTitle2", "test, test2!", "2015/10/02");
        mData.add(review2);
    }

//
//    class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ViewHolder> {
//        Activity mActivity;
//        LayoutInflater mLayoutInflater;
//        ArrayList<Review> Reviews;
//        View mView;
//
//        public CommentsListAdapter(Activity activity, ArrayList<Review> data) {
//            mActivity = activity;
//            mLayoutInflater = LayoutInflater.from(activity);
//            Reviews = data;
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            public ViewHolder(View arg0) {
//                super(arg0);
//                mView = arg0;
//            }
//
//            TextView userId;
//            TextView user_point;
//            TextView user_comment;
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return mData.size();
//        }
//
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View view = mLayoutInflater.inflate(R.layout.item_user_comment, viewGroup, false);
//            ViewHolder viewHolder = new ViewHolder(view);
//            viewHolder.userId = (TextView) view.findViewById(R.id.text_userId);
//            viewHolder.user_point = (TextView) view.findViewById(R.id.text_user_point);
//            viewHolder.user_comment = (TextView) view.findViewById(R.id.text_user_comment);
//
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
//            Review review = Reviews.get(i);
//            viewHolder.userId.setText(review.getAuthor());
//            viewHolder.user_point.setText("評分");
//            viewHolder.user_comment.setText(review.getContent());
//
//        }
//
//
//    //end of Adapter
//    }

//    private class CommentTask extends AsyncTask{
//
//        @Override
//        protected Object doInBackground(Object[] params){
//            ArrayList<Review> tempReviews = ReviewAPI.getReviewsByMovieID(movie_id, mPage);
//            if(tempReviews != null && tempReviews.size() > 0){
//                mData.addAll(tempReviews);
//                mPage++;
//                return true;
//            }
//            return false;
//        }
//
//        @Override
//        protected void onPostExecute(Object result){
//            if((boolean) result){
//                if(mAdapter == null){
//                    mAdapter = new CommentsListAdapter(CommentActivity.this, mData);
//                    mRecyclerView.setAdapter(mAdapter);
//                }else {
//                    mAdapter.notifyDataSetChanged();
//                }
//            }else{
//                Toast.makeText(CommentActivity.this, "沒有其他評論", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }//end of CommentTask



}
