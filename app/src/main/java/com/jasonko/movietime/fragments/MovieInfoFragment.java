package com.jasonko.movietime.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jasonko.movietime.MoviePhotosActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.TrailersActivity;
import com.jasonko.movietime.api.MovieAPI;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.FollowMovie;
import com.jasonko.movietime.dao.FollowMovieDao;
import com.jasonko.movietime.dao.RecentMovie;
import com.jasonko.movietime.dao.RecentMovieDao;
import com.jasonko.movietime.imageloader.ImageLoader;
import com.jasonko.movietime.model.Movie;
import com.jasonko.movietime.tool.NetworkUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kolichung on 8/27/15.
 */
public class MovieInfoFragment extends Fragment{

    public static final String ARG_MOVIE_ID = "MOVIE_ID";

    private int mMovieID;
    private Movie mMovie;

    private CardView photoCardView;
    private CardView trailerCardView;
    private TextView readmoreTextView;

    private TextView photoText;
    private TextView trailerText;
    private TextView infoText;
    private TextView typeText;
    private TextView diretorText;
    private TextView actorText;
    private TextView officerText;
    private TextView titleText;
    private TextView titleEngText;
    private TextView classText;
    private TextView publishDateText;
    private ImageView mImage;

    private ImageLoader mImageLoader;
    private ProgressBar mProgressBar;
    private LinearLayout mLinearLayout;
    private TextView noNetWorkText;

    private ImageView follow_bottom_image;
    private ImageView follow_image;
    private LinearLayout linearAddFollow;
    private LinearLayout linearShare;
    private LinearLayout linearResponse;

    private DaoMaster.DevOpenHelper helper;

    public static MovieInfoFragment newInstance(int movie_id) {
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movie_id);
        MovieInfoFragment fragment = new MovieInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieID = getArguments().getInt(ARG_MOVIE_ID);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_info, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.my_progress_bar);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.movieinfo_layout);
        noNetWorkText = (TextView) view.findViewById(R.id.no_network_text);

        photoCardView = (CardView) view.findViewById(R.id.movie_photos_card_view);
        trailerCardView = (CardView) view.findViewById(R.id.movie_trailers_card_view);
        readmoreTextView = (TextView) view.findViewById(R.id.textview_movie_info_readmore);

        titleText = (TextView) view.findViewById(R.id.movieinfo_text_title);
        titleEngText = (TextView) view.findViewById(R.id.movieinfo_text_title_eng);
        classText = (TextView) view.findViewById(R.id.movieinfo_text_class);
        publishDateText = (TextView) view.findViewById(R.id.movieinfo_text_publish_date);
        mImage = (ImageView) view.findViewById(R.id.movieinfo_image);

        photoText = (TextView) view.findViewById(R.id.movieinfo_text_photos);
        trailerText = (TextView) view.findViewById(R.id.movieinfo_text_trailers);
        infoText = (TextView) view.findViewById(R.id.movieinfo_text_info);
        typeText = (TextView) view.findViewById(R.id.movieinfo_text_type);
        diretorText = (TextView) view.findViewById(R.id.movieinfo_text_director);
        actorText = (TextView) view.findViewById(R.id.movieinfo_text_actors);
        officerText = (TextView) view.findViewById(R.id.movieinfo_text_officier);

        follow_image = (ImageView) view.findViewById(R.id.follow_movie_up_image);
        linearAddFollow = (LinearLayout) view.findViewById(R.id.linearLayout_movie_add_follow);
        follow_bottom_image = (ImageView) view.findViewById(R.id.follow_movie_bottom_image);
        linearShare = (LinearLayout) view.findViewById(R.id.linearLayout_movie_share);
        linearResponse = (LinearLayout) view.findViewById(R.id.linearLayout_movie_response);

        mImageLoader = new ImageLoader(getActivity());
        readmoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readmoreTextView.getTag() == null || (int) readmoreTextView.getTag() == 0) {
                    infoText.setMaxLines(Integer.MAX_VALUE);
                    infoText.setText(Html.fromHtml(mMovie.getMovie_info()));
                    readmoreTextView.setTag(1);
                    readmoreTextView.setText("收起簡介內容");
                } else {
                    infoText.setMaxLines(4);
                    infoText.setText(Html.fromHtml(mMovie.getMovie_info()));
                    readmoreTextView.setTag(0);
                    readmoreTextView.setText("閱讀完整內容");
                }
            }
        });

        if (NetworkUtil.getConnectivityStatus(getActivity()) != 0 ){
            new SingleMovieTask().execute();
        }else {
            noNetWorkText.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }

        return view;
    }

    private class SingleMovieTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            mMovie = MovieAPI.getSingleMovie(mMovieID);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {

            if (mMovie != null) {
                titleText.setText(mMovie.getTitle());
                titleEngText.setText(mMovie.getTitle_eng());
                classText.setText(mMovie.getMovie_class() + " 片長：" + mMovie.getMovie_length());
                publishDateText.setText("上映日期：" + mMovie.getPublish_date());

                infoText.setText(Html.fromHtml(mMovie.getMovie_info()));
                typeText.setText("類型:\n" + mMovie.getMovie_type());
                diretorText.setText("導演:\n" + mMovie.getDirector());
                actorText.setText("演員:\n" + mMovie.getActors());
                officerText.setText("出品公司:\n" + mMovie.getOfficial());
                photoText.setText("圖集(" + Integer.toString(mMovie.getPhoto_size()) + ")");
                trailerText.setText("影片(" + Integer.toString(mMovie.getTrailer_size()) + ")");

                mImageLoader.DisplayImage(mMovie.getSmall_pic(), mImage);

                photoCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newIntent = new Intent(getActivity(), MoviePhotosActivity.class);
                        newIntent.putExtra("photo_size", mMovie.getPhoto_size());
                        newIntent.putExtra("movie_id", mMovie.getMovie_id());
                        newIntent.putExtra("big_photo_url", mMovie.getLarge_pic());
                        getActivity().startActivity(newIntent);
                    }
                });

                trailerCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newIntent = new Intent(getActivity(), TrailersActivity.class);
                        newIntent.putExtra("movie_id", mMovie.getMovie_id());
                        newIntent.putExtra("movie_title", mMovie.getTitle());
                        getActivity().startActivity(newIntent);
                    }
                });

                mProgressBar.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.VISIBLE);

                follow_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkFollow()) {
                            deleteFollow();
                            follow_image.setImageResource(R.drawable.icon_tag);
                            follow_bottom_image.setImageResource(R.drawable.icon_tag);
                            Toast.makeText(getActivity(), "取消追蹤", Toast.LENGTH_SHORT).show();
                        } else {
                            addFollow();
                            follow_image.setImageResource(R.drawable.icon_tag_full);
                            follow_bottom_image.setImageResource(R.drawable.icon_tag_full);
                            Toast.makeText(getActivity(), "追蹤此電影", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                linearAddFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkFollow()) {
                            deleteFollow();
                            follow_image.setImageResource(R.drawable.icon_tag);
                            follow_bottom_image.setImageResource(R.drawable.icon_tag);
                            Toast.makeText(getActivity(), "取消追蹤", Toast.LENGTH_SHORT).show();
                        } else {
                            addFollow();
                            follow_image.setImageResource(R.drawable.icon_tag_full);
                            follow_bottom_image.setImageResource(R.drawable.icon_tag_full);
                            Toast.makeText(getActivity(), "追蹤此電影", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                linearShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentShare = new Intent(Intent.ACTION_SEND);
                        intentShare.setType("text/plain");
                        intentShare.putExtra(Intent.EXTRA_SUBJECT, "電影即時通 APP !");
                        intentShare.putExtra(Intent.EXTRA_TEXT, "走走走,看電影:" + mMovie.getTitle()+"~ from 電影即時通APP \n https://play.google.com/store/apps/details?id=com.jasonko.movietime");
                        try {
                            startActivity(Intent.createChooser(intentShare, "分享 ..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                linearResponse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentReport = new Intent(Intent.ACTION_SEND);
                        intentReport.setType("text/plain");
                        intentReport.putExtra(Intent.EXTRA_EMAIL, new String[]{"kosbrotherschool@gmail.com"});
                        intentReport.putExtra(Intent.EXTRA_SUBJECT, "問題回報："+ mMovie.getTitle() +" "+ Integer.toString(mMovie.getMovie_id()));
                        try {
                            startActivity(Intent.createChooser(intentReport, "傳送 mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                addThisMovieToRecentMovie();

                if (checkFollow()) {
                    follow_image.setImageResource(R.drawable.icon_tag_full);
                    follow_bottom_image.setImageResource(R.drawable.icon_tag_full);
                }
            }else {
                noNetWorkText.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }

    private void addFollow() {

        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FollowMovieDao followMovieDao = daoSession.getFollowMovieDao();

        Date publish_date_date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            publish_date_date = sdf.parse(mMovie.getPublish_date());
        }catch (Exception e){

        }


        FollowMovie newFollowMovie = new FollowMovie(null, mMovie.getTitle(), mMovie.getMovie_class(), mMovie.getSmall_pic(), mMovie.getMovie_id(), publish_date_date, new Date());
        followMovieDao.insert(newFollowMovie);

    }

    private void deleteFollow() {

        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FollowMovieDao followMovieDao = daoSession.getFollowMovieDao();
        List followMovieList = followMovieDao.queryBuilder().where(FollowMovieDao.Properties.Movie_id.eq(mMovie.getMovie_id())).list();
        followMovieDao.delete((FollowMovie)followMovieList.get(0));

    }

    private boolean checkFollow() {

        if (helper!=null){
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            FollowMovieDao followMovieDao = daoSession.getFollowMovieDao();
            List followMovieList = followMovieDao.queryBuilder().where(FollowMovieDao.Properties.Movie_id.eq(mMovie.getMovie_id())).list();
            if (followMovieList.size() > 0){
                return true;
            }else {
                return false;
            }
        }
        return false;

    }

    private void addThisMovieToRecentMovie() {

        helper = new DaoMaster.DevOpenHelper(getActivity(), "expense", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();

        RecentMovieDao movieDao = daoSession.getRecentMovieDao();
        List recentMovies = movieDao.queryBuilder()
                .where(RecentMovieDao.Properties.Movie_id.eq(mMovie.getMovie_id()))
                .list();
        if (recentMovies.size() == 0) {
            RecentMovie newRencentMovie = new RecentMovie(null, mMovie.getTitle(), mMovie.getMovie_class(),
                    mMovie.getMovie_type(), mMovie.getActors(), mMovie.getPublish_date(), mMovie.getSmall_pic(), mMovie.getMovie_id(), new Date());
            movieDao.insert(newRencentMovie);
        }else {
            RecentMovie theMovie = (RecentMovie)recentMovies.get(0);
            theMovie.setUpdate_date(new Date());
            movieDao.update(theMovie);
        }

        List allRecentMovies = movieDao.queryBuilder().orderAsc(RecentMovieDao.Properties.Update_date).list();
        if (allRecentMovies.size()> 10){
            movieDao.delete((RecentMovie)allRecentMovies.get(0));
        }

    }

}
